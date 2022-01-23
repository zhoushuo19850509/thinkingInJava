package com.nbcb.thinkingInJava.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个注解
 * 用于指定table name
 */
@Target(ElementType.TYPE)  // 注解在类名上生效
@Retention(RetentionPolicy.RUNTIME)  // 注解在程序运行时生效
public @interface DBTable {
    public String name() default  "";  // 指定表名
}