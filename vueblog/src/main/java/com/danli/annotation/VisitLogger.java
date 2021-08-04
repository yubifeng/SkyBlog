package com.danli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author fanfanli
 * @date  2021/5/3
 */
//注解用于方法
@Target(ElementType.METHOD)
//运行时使用
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {
    String behavior() default "" ;
    String content() default "";

}
