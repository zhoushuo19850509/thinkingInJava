package com.nbcb.thinkingInJava.strings.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个代码在上一个代码TestRegulerExpression.java基础上，
 * 继续讨论Matcher.find()方法，如何遍历一个字符串中所有符合正则要求的内容
 */
public class Finding {
    public static void main(String[] args) {

        // "\w+"这个正则表达式，意思就是一个或者多个word [a-zA-Z_0-9]
        Matcher m = Pattern.compile("\\w+").
                matcher("Evening is full of the linnet's wings");

        /**
         * 下面这个while循环，遍历上述字符串，
         * 把所有符合正则表达式要求的内容，意思打印出来
         * find()就像是itarator，遍历所有符合正则表达式要求的内容
         */
        while(m.find()){
            System.out.println(m.group() + " ");
        }

        System.out.println("=============");

        /**
         * 我们在这里尝试再次调用find()方法
         * 正如iterator一样，因为在上一个while循环中把符合要求的内容都遍历完了，
         * 这个while就没啥要打印的了
         */
        while(m.find()){
            System.out.println(m.group() + " ");
        }

        System.out.println(">>>>>>>>>>>>>");


        /**
         * 第三次尝试调用find()方法
         * 这次find()方法加入了一个数字，这个数字参数是啥意思呢？
         * 先reset一下，然后告诉find()方法从字符串哪个位置开始扫描
         */
        int i = 0;
        while(m.find(i)){
            System.out.println("[" +i + "] " +m.group() + " ");
            i++;
        }


    }
}
