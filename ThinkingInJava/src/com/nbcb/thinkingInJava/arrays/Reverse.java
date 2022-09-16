package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;
import java.util.Collections;

import static com.nbcb.thinkingInJava.arrays.CompType.generator;


/**
 * 这个是沿袭CompType.java
 * 只不过这次是对数组CompType[]进行逆序排序
 */
public class Reverse {

    public static void main(String[] args) {
        CompType[] compTypes = Generated.array(new CompType[12],generator());
        System.out.println("before sorting ...");
        System.out.println(Arrays.toString(compTypes));

        Arrays.sort(compTypes, Collections.reverseOrder());
        System.out.println("after sorting in reverse order ...");
        System.out.println(Arrays.toString(compTypes));
    }

}
