package com.nbcb.thinkingInJava.strings.format;

/**
 * 要实现output format有3种方式，我觉得最好的还是format
 * 后续打印日志参考这个方式吧
 */
public class SimpleFormat {
    public static void main(String[] args) {
        String x = "hello pi";
        double y = 3.1415;
        System.out.println( " print the result : [" + x + ", " + y + "]");
        System.out.printf(" print the result : [%s, %f] \n", x,y);
        System.out.format(" print the result : [%s, %f] \n", x,y);
    }
}
