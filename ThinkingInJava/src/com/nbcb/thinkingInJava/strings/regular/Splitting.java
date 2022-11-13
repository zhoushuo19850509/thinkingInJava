package com.nbcb.thinkingInJava.strings.regular;

import java.util.Arrays;

/**
 * 之前，我们调用String split()方法，一般都是以" "/","这些进行分隔
 * 这个代码告诉我们，可以通过regular expression进行分隔，功能更加强大
 */
public class Splitting {

    public static String knights = "Then, when you have found the shrubbery, you must " +
            "cut down the mighties tree in the forest ...";

    public static void split(String reg){
        System.out.println(Arrays.toString(knights.split(reg)));
    }

    public static void main(String[] args) {
        // 纯粹以空格进行分隔
        split(" ");
        // W+代表non word，所有非word的，都能够分隔，那么对于"Then, when"，就是以", "进行分隔
        split("\\W+");
        // n\W+代表以字符"n"开头，后面接一个non word，依次作为分隔的依据
        split("n\\W+");
    }
}
