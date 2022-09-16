package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;
import java.util.Collections;

/**
 * 这个代码是用于演示如何对String[]进行各种方式的排序
 */
public class StringSorting {
    public static void main(String[] args) {
        String[] strings = Generated.array(new String[20],
                new RandomGenerator.RandomString(5));
        System.out.println("before sorting ...");
        System.out.println(Arrays.toString(strings));

        Arrays.sort(strings);
        System.out.println("after sorting asc...");
        System.out.println(Arrays.toString(strings));

        Arrays.sort(strings, Collections.reverseOrder());
        System.out.println("after sorting desc...");
        System.out.println(Arrays.toString(strings));

        Arrays.sort(strings,String.CASE_INSENSITIVE_ORDER);
        System.out.println("after sorting CASE_INSENSITIVE_ORDER...");
        System.out.println(Arrays.toString(strings));

    }
}
