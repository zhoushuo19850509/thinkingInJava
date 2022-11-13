package com.nbcb.thinkingInJava.strings.regular;

/**
 * 数字匹配
 */
public class IntegerMatch {
    public static void main(String[] args) {
        // ? one or none
        System.out.println("-123".matches("-?\\d+"));
        System.out.println("123".matches("-?\\d+"));
        System.out.println("+123".matches("-?\\d+"));

        // (-|\\+)? 意思就是以+/-开头，或者没有符号
        System.out.println("+123".matches("(-|\\+)?\\d+"));
    }
}
