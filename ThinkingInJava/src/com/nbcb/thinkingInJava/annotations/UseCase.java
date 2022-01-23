package com.nbcb.thinkingInJava.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个注解
 * 风格类似一个接口
 */
@Target(ElementType.METHOD)  // 注解在方法上生效
@Retention(RetentionPolicy.RUNTIME)  // 注解在程序运行时生效
public @interface UseCase {
    public int id();
    public String description() default  "no description";
}
