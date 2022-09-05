package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;

/**
 * array comparing
 */
public class ComparingArrays {
    public static void main(String[] args) {
        // 先比对两个int array
        int[] a1 = new int[10];
        int[] a2 = new int[10];
        Arrays.fill(a1, 11);
        Arrays.fill(a2, 11);
        System.out.println(Arrays.equals(a1,a2));
        a1[2] = 33;
        System.out.println(Arrays.equals(a1,a2));

        /**
         * 这个要说明一下
         * String array b1 包含5个元素，指向同一个对象
         * String array b2 包含5个元素，分别指向5个不同的对象
         * 为啥Arrays.equals(b1,b2)返回true呢？
         * 我们可以猜测一下，Arrays.equals(b1,b2)，是把两个数组每个对象执行一下equals()方法
         */
        String[] b1 = new String[5];
        Arrays.fill(b1, "H1");
        String[] b2 = {new String("H1"),new String("H1"),
                new String("H1"),new String("H1"),new String("H1")};
        System.out.println(Arrays.equals(b1,b2));

    }
}
