//package com.danli.aspect;
//
//
//import javafx.beans.binding.ObjectExpression;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.ServletRequestAttributeEvent;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * AOP记录操作日志
// * @Author: fanfanli
// * @Date: 2021/5/3
// */
//
//@Aspect
//@Component
//public class OperationLogAspect {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * 配置切入点
//     */
//    @Pointcut("execution(* com.danli.controller.*.*(..))")
//    public void log(){}
//
//    @Before("log()")
//    public void doBeforw(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String url = request.getRequestURL().toString();
//        String ip = request.getHeader("x-forwarded-for");
//        String userAgent = request.getHeader("User-Agent");
//        String clasaMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//        RequestLog requestLog =new RequestLog(url,ip,userAgent,clasaMethod,args);
//        logger.info("Result : {}",requestLog);
//
//    }
//
//    @After("log()")
//    public void doAfter() {
//        logger.info("----------After----------");
//
//    }
//
//
//    @AfterReturning(returning = "result",pointcut = "log()")
//    public void doAfterReturn(Object result) {
//        logger.info("Result : {}",result);
//
//    }
//
//    @Data
//    @AllArgsConstructor
//    public class RequestLog {
//        private String url;
//        private String ip;
//        private String userAgent;
//        private String classMethod;
//        private Object[] args;
//
//
//    }
//
//
//
//
//}
