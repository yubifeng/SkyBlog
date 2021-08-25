package com.danli.aspect;

import cn.hutool.json.JSONObject;
import com.danli.annotation.VisitLogger;
import com.danli.common.lang.Result;
import com.danli.config.RedisKeyConfig;
import com.danli.entity.Blog;
import com.danli.entity.VisitLog;
import com.danli.entity.Visitor;
import com.danli.service.RedisService;
import com.danli.service.VisitLogService;
import com.danli.service.VisitorService;
import com.danli.util.IpAddressUtils;
import com.danli.util.UserAgentUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;


/**
 * AOP记录访问日志
 *
 * @author fanfanli
 * @date  2021/5/3
 */
@Component
@Aspect
public class VisitLogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    VisitLogService visitLogService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    UserAgentUtils userAgentUtils;
    @Autowired
    RedisService redisService;


    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(visitLogger)")
    public void log(VisitLogger visitLogger){}

    /**
     * 配置环绕通知
     *
     * @param joinPoint 连接点
	 * @return 返回方法执行后的结果
	 */
    @Around("log(visitLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint,VisitLogger visitLogger) throws Throwable {

        currentTime.set(System.currentTimeMillis());
        //获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //让目标方法执行 获取返回的结果
        Object result = joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //校验访客标识码
        String identification = checkIdentification(request);
        //异步保存至数据库
        saveVisitLog(joinPoint, visitLogger, request, result, times, identification);

        return result;
    }



    void saveVisitLog(){

    }



    /**
     * 异步设置VisitLogger对象属性并保存到数据库中
     *
     * @param joinPoint
     * @param visitLogger
     * @param result
     * @param times
     * @return
     */
    @Async
    void saveVisitLog(ProceedingJoinPoint joinPoint, VisitLogger visitLogger, HttpServletRequest request, Object result,
                               int times, String identification) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String behavior = visitLogger.behavior();
        String content = visitLogger.content();
        String ip = request.getHeader("x-forwarded-for");


        String ipSource = IpAddressUtils.getCityInfo(ip);
        String userAgent = request.getHeader("User-Agent");
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");



        //获取参数名和参数值
        Map<String, Object> requestParams = new LinkedHashMap<>();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if( args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile){
                continue;
            }
            requestParams.put(parameterNames[i], args[i]);
        }
        //根据访问内容和返回的结果判断访问的内容并进行备注
        Map<String, String> map = judgeBehavior(behavior, content, requestParams, result);
        VisitLog log = new VisitLog(null,identification, uri, method, new JSONObject(requestParams).toString(), behavior, map.get("content"),map.get("remark"), ip,ipSource,os,browser,LocalDateTime.now(),times, userAgent);

        visitLogService.saveOrUpdate(log);
    }



    /**
     * 根据访问行为，设置对应的访问内容或备注
     *
     * @param behavior
     * @param content
     * @param requestParams
     * @param result
     * @return 返回内容和备注为主键的map
     */
    private Map<String, String> judgeBehavior(String behavior, String content, Map<String, Object> requestParams, Object result) {
        Map<String, String> map = new HashMap<>();
        String remark = "";
        if (behavior.equals("访问页面") && (content.equals("首页"))) {
            int pageNum = (int) requestParams.get("currentPage");
            remark = "第" + pageNum + "页";
        } else if (behavior.equals("查看博客")) {
            Result res = (Result) result;
            if (res.getCode() == 200) {
                Blog blog = (Blog) res.getData();
                String title = blog.getTitle();
                content = title;
                remark = "文章标题：" + title;
            }
        } else if (behavior.equals("搜索博客")) {
            Result res = (Result) result;
            if (res.getCode() == 200) {
                String query = (String) requestParams.get("queryString");
                content = query;
                remark = "搜索内容：" + query;
            }
        } else if (behavior.equals("查看分类")) {
            String categoryName = (String) requestParams.get("typeName");
            int pageNum = (int) requestParams.get("currentPage");
            content = categoryName;
            remark = "分类名称：" + categoryName + "，第" + pageNum + "页";
        } else if (behavior.equals("点击友链")) {
            String nickname = (String) requestParams.get("nickname");
            content = nickname;
            remark = "友链名称：" + nickname;
        }
        map.put("remark", remark);
        map.put("content", content);
        return map;
    }




    /**
     * 校验访客标识码
     *
     * @param request
     * @return 访客标识码UUID
     */
    private String checkIdentification(HttpServletRequest request) {
        String identification = request.getHeader("identification");
        if (identification == null) {
            //第一次访问，签发uuid并保存到数据库和Redis
            identification =   UUID.randomUUID().toString();
            saveUUID(identification,request);
        } else {
            //校验Redis中是否存在uuid
            boolean redisHas = redisService.hasValueInSet(RedisKeyConfig.IDENTIFICATION_SET, identification);

            //Redis中不存在uuid
            if (!redisHas) {
                //校验数据库中是否存在uuid
                boolean mysqlHas = visitorService.hasUUID(identification);
                if (mysqlHas) {
                    //数据库存在，保存至Redis
                    redisService.saveValueToSet("identificationSet", identification);
                    //更新最后访问时间和pv
                    updateVistor(identification);

                } else {
                    //数据库不存在，签发新的uuid
                    identification =   UUID.randomUUID().toString();
                    //异步保存
                    saveUUID(identification,request);
                }
            }
            else{
                //更新最后时间和pv
                updateVistor(identification);
            }
        }
        return identification;
    }





    @Async
    void updateVistor(String identification) {
        //更新最后访问时间和pv
        Visitor visitor = visitorService.getVisitorByUuid(identification);
        visitor.setPv(visitor.getPv()+1);
        visitor.setLastTime(LocalDateTime.now());
        visitorService.saveOrUpdate(visitor);
    }


    /**
     * 异步保存UUID至数据库和Redis
     *
     * @param request
     * @return UUID
     */
    @Async
    void saveUUID(String uuid,HttpServletRequest request) {

        //保存至Redis
        redisService.saveValueToSet(RedisKeyConfig.IDENTIFICATION_SET, uuid);
        //获取响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //添加访客标识码UUID至响应头
        response.addHeader("identification", uuid);
        //暴露自定义header供页面资源使用
        response.addHeader("Access-Control-Expose-Headers", "identification");

        //获取访问者基本信息
        String ip = request.getHeader("x-forwarded-for");

        String userAgent = request.getHeader("User-Agent");
        String ipSource = IpAddressUtils.getCityInfo(ip);
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        Visitor visitor = new Visitor(null,uuid, ip,ipSource,os,browser,LocalDateTime.now(),LocalDateTime.now(),1, userAgent);
        //保存至数据库
        visitorService.saveOrUpdate(visitor);
    }
}
