package com.nbcb.thinkingInJava.strings.basic;


/**
 * 这个代码是为了说明String对象拼接的原理
 *
 * 1.把Concatenation.java编译成Concatenation.class
 *   javac Concatenation.java
 * 2.把Concatenation.class文件(bytecode)转化为的具备注释的bytecode文件
 * 3.从bytecode文件中可以看到，多个String拼接，JVM的内部实现是创建一个StringBuilder对象
 *   然后调用StringBuilder对象的append()方法，处理各个String
 *
 * 后续可以用这个方式分析JVM是如何解析java源码的，有助于我们后续解读JVM实现机制
 */
public class Concatenation {
    public static void main(String[] args) {
        System.out.println("start Concatenation ... ");
        String a = "hello";
        String b = "gogogo " + a + " world ..." + 12347;
        System.out.println(b);
    }
}
