package com.nbcb.thinkingInJava.strings.regular;

public class Replacing {
    public static void main(String[] args) {

        /**
         * f\\w+ 的意思是以"f"开头的word
         * replaceFirst()的意思是把第一个以"f"开头的word，替换成"localtion"
         */
        System.out.println(
                Splitting.knights.replaceFirst("f\\w+","location"));

        /**
         * replaceAll的意思是把第所有以"f"开头的word，替换成"localtion"
         */
        System.out.println(
                Splitting.knights.replaceAll("f\\w+","location"));

        /**
         * 特别注意，以下的替换方法replace()不生效！！！
         * 因为replace()不支持regular expression ...
         *
         */
        System.out.println(
                Splitting.knights.replace("f\\w+","location"));


    }
}
