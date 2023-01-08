package com.nbcb.thinkingInJava.strings.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个代码是要体现group。group啥意思呢？
 * 比如pattern是 A(B(C))D，那么按照这个pattern匹配到的完整内容,就是group[0]
 * 然后依次 ：
 * group[1]是BC
 * group[2]是C
 *
 * 啥意思懂吧？就是按照从左到右，从外到内的顺序，把所有括号的内容放到group数组中
 * 以后我们如果在pattern中有括号，那么可以通过group的形式，获取括号中各个层级的内容
 */
public class Groups {
    public static void main(String[] args) {
        String POEM =   "Twas brillig, and the slithy toves\n" +
                        "Did gyre and gimble in the wabe.\n" +
                        "All mimsy were the borogoves,\n" +
                        "And the mome raths outgrabe.\n\n" +
                        "Beware the Jabberwock, my son,\n" +
                        "The jaws that bite, the claws that catch.\n" +
                        "Beware the Jubjub bird, and shun\n" +
                        "The frumious Bandersnatch.";

        /**
         * 这个pattern要好好解释一下
         * 1.(?m) 是pattern flag意思是匹配多行，具体参考ReFlag.java就明白了
         * 2.\S+意思是non-whitespace字符
         * 3.\s+意思是whitespace字符
         * 4.整理一下上述的pattern，意思就是找出每行结尾的三个字符
         *   具体参考"display[1]"
         * 5.为了体现group，会把所有括号中的内容都放到group数组中
         *   具体参考"display[2]"
         */
        String pattern = "(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$";
        Matcher m = Pattern.compile(pattern).matcher(POEM);

        /**
         * 打印所有符合pattern的内容
         * 如果不懂，可以参考Finding.java
         */
//        System.out.println("=======display[1]=========");
//        while(m.find()){
//            System.out.println("[" + m.group() + "]");
//        }

        System.out.println("=======display[2]=========");
        while(m.find()){
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.print("[" + m.group(i) + "] ");
            }
            System.out.println();
        }


    }
}
