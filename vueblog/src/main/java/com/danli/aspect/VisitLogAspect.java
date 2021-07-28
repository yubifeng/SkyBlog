package com.danli.aspect;

import cn.hutool.core.bean.BeanUtil;
import com.danli.entity.Blog;
import com.danli.entity.VisitLog;
import com.danli.entity.Visitor;
import com.danli.service.RedisService;
import com.danli.service.VisitLogService;
import com.danli.service.VisitorService;
import com.danli.util.IpAddressUtils;
import com.danli.util.ShiroUtil;
import com.danli.util.UserAgentUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


/**
 * @Description: AOP记录访问日志
 * @Author: fanfanli
 * @Date: 2021-05-03
 */


@Component
@Aspect
public class VisitLogAspect {


    @Autowired
    VisitLogService visitLogService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    UserAgentUtils userAgentUtils;
    @Autowired
    RedisService redisService;
    




    /**
     * 配置切入点
     */
    //@Pointcut("execution(* com.danli.controller.*.*(..))")
    @Pointcut("execution(* com.danli.controller.BlogController.*(..))")
    public void log(){}

    @Before("log()")
    public void doBeforw(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getHeader("x-forwarded-for");
        //System.out.println("ip地址"+ip);
        String userAgent = request.getHeader("User-Agent");
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        String args = joinPoint.getArgs().toString();
        VisitLogAspect.VisitQuestyLog visitQuestyLog =new VisitLogAspect.VisitQuestyLog(url,ip,userAgent,classMethod,args);


        //visitLogService.saveVisitLog(visitLog);

        //校验访客标识码
        String identification = checkIdentification(request);

        String ipSource = IpAddressUtils.getCityInfo(visitQuestyLog.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(visitQuestyLog.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        //保存至数据库
        VisitLog temp = temp = new VisitLog();
        temp.setCreateTime(LocalDateTime.now());
        temp.setBrowser(browser);
        temp.setOs(os);
        temp.setIpSource(ipSource);
        temp.setUuid(identification);
        BeanUtil.copyProperties(visitQuestyLog, temp, "id", "createTime","browser","os");
        visitLogService.saveOrUpdate(temp);




    }

    @After("log()")
    public void doAfter() {


    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
    }


    /**
     * 校验访客标识码
     *
     * @param request
     * @return
     */
    private String checkIdentification(HttpServletRequest request) {
        String identification = request.getHeader("identification");
        if (identification == null) {
            //第一次访问，签发uuid并保存到数据库和Redis
            identification = saveUUID(request);
        } else {
            //校验Redis中是否存在uuid
            boolean redisHas = redisService.hasValueInSet("identificationSet", identification);
            //Redis中不存在uuid
            if (!redisHas) {
                //校验数据库中是否存在uuid
                boolean mysqlHas = visitorService.hasUUID(identification);
                if (mysqlHas) {
                    //数据库存在，保存至Redis
                    redisService.saveValueToSet("identificationSet", identification);
                } else {
                    //数据库不存在，签发新的uuid
                    identification = saveUUID(request);
                }
            }
        }
        return identification;
    }

    /**
     * 签发UUID，并保存至数据库和Redis
     *
     * @param request
     * @return
     */
    private String saveUUID(HttpServletRequest request) {
        //获取响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //生成UUID
        String uuid = UUID.randomUUID().toString();
        //添加访客标识码UUID至响应头
        response.addHeader("identification", uuid);
        //暴露自定义header供页面资源使用
        response.addHeader("Access-Control-Expose-Headers", "identification");
        //保存至Redis
        redisService.saveValueToSet("identificationSet", uuid);
        //获取访问者基本信息
        String ip = request.getHeader("x-forwarded-for");
        String userAgent = request.getHeader("User-Agent");
        String ipSource = IpAddressUtils.getCityInfo(ip);
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        Visitor visitor = new Visitor(null,uuid, ip,ipSource,os,browser,LocalDateTime.now(),LocalDateTime.now(),0, userAgent);
        //保存至数据库
        visitorService.saveOrUpdate(visitor);
        return uuid;
    }
    @Data
    @AllArgsConstructor
    public class VisitQuestyLog {
        private String url;
        private String ip;
        private String userAgent;
        private String classMethod;
        private String args;

    }

}
