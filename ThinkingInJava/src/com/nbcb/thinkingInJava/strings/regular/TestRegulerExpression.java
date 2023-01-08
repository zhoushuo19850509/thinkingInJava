package com.nbcb.thinkingInJava.strings.regular;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 之前我们在Rudolph.java中使用了正则表达式的基础用法：
 * "Rudolph".matches(pattern)
 * 一旦在字符串中发现符合正则，就返回
 *
 * 这个代码中，我们进行了扩展。
 * 一个字符串中符合正则的可能会有很多，会有一串
 * 这个代码会按顺序找出字符串中所有符合正则的内容，打印出来
 *
 */
public class TestRegulerExpression {

    /**
     * 这个程序为了说明正则表达式
     *
     * 程序会遍历各个参数(各个正则表达式)，把符合正则表达式要求的结果打印出来
     *
     * @param args
     * 参数1 一个字符串
     * 参数1、2、3、...n 一串正则表达式
     * 比如： abcabcabcdefabc abc+ (abc)+ (abc){2,}
     */
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: \n" +
                    "java TestRegulerExpression [字符串] [正则表达式(可多个)]");
        }

        System.out.println("Input: " + args[0]);
        for(String arg : args){
            System.out.println("regular expression: " + arg);
            Pattern p = Pattern.compile(arg);   // 当前正则表达式
            Matcher m = p.matcher(args[0]);     // 符合正则表达式的Matcher
            // 为啥要while()循环呢？因为某个字符串符合正则表达式的地方可能会有多处
            while(m.find()){
                System.out.println(" Match " + m.group() +
                        " at " + m.start() + "-" + (m.end() - 1) );
            }
        }


    }
}
