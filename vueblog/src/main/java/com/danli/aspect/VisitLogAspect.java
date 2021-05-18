package com.danli.aspect;

import cn.hutool.core.bean.BeanUtil;
import com.danli.entity.Blog;
import com.danli.entity.VisitLog;
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
import java.time.LocalDateTime;
import java.util.Map;


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

        BeanUtil.copyProperties(visitQuestyLog, temp, "id", "createTime","browser","os");
        visitLogService.saveOrUpdate(temp);

    }

    @After("log()")
    public void doAfter() {


    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
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
