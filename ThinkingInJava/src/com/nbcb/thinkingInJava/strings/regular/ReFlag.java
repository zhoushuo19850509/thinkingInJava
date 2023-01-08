package com.nbcb.thinkingInJava.strings.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个代码主要是为了说明Pattern flag的用法，比如
 *
 * Pattern.CASE_INSENSITIVE  ： 不区分大小写
 * Pattern.MULTILINE   ： 匹配大小写
 *
 */
public class ReFlag {
    public static void main(String[] args) {
        System.out.println("start ReFlag ...");

        String str = "java has regex\nJava has regex\n" +
                        "JAVA has pretty good regular expressions\n" +
                        "Regular expressions are in Java";

        /**
         * 下面注释的中断代码，是为了说明(?m)和Pattern.MULTILINE的功能差不多
         */
//        Pattern p = Pattern.compile("(?m)^java",
//                Pattern.CASE_INSENSITIVE);
        Pattern p = Pattern.compile("^java",
                Pattern.CASE_INSENSITIVE| Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        while(m.find()){
            System.out.println(m.group());
        }

    }
}
