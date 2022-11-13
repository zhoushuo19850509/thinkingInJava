package com.nbcb.thinkingInJava.strings.regular;

/**
 * 这个代码很直接，就是尝试各种pattern，
 * 看我们的string: "Rudolph"是否匹配这个pattern
 */
public class Rudolph {
    public static void main(String[] args) {
        /**
         * 1.pattern1
         * R.* 意思是以"R"开头的任意长度的字符，因为"."是任意字符，"*"是zero or more
         * 那么".*"就是任意长度的任意字符
         * 2.pattern2
         * (R|r)[aeiou][a-z]ol.*
         * 这个就有点整花活的意思了
         * 第一个字符： (R|r) 以R/r开头
         * 第二个字符： [aeiou] 包含aeiou其中任意一个
         * 第三个字符： [a-z] a-z之间任意一个字符
         * 第四、五个字符： 必须是"ol"
         * 后续就是任意长度的字符
         */
        for(String pattern : new String[]{"Rudolph","(R|r)udolph",
        "R.*","(R|r)[aeiou][a-z]ol.*"}){
            System.out.println("Rudolph".matches(pattern));
        }
    }
}
