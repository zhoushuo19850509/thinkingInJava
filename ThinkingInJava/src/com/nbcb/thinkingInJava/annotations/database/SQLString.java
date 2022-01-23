package com.nbcb.thinkingInJava.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个注解
 * 用于指定String类型的字段
 */
@Target(ElementType.FIELD)  // 注解在属性名上生效
@Retention(RetentionPolicy.RUNTIME)  // 注解在程序运行时生效
public @interface SQLString {
    int value() default 0;  // 指定String类型字段的长度
    String name() default "";
    Constraints constraints() default @Constraints;
}