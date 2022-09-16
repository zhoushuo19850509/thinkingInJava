package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;

/**
 * 这个代码主要是为了说明array copy功能
 */
public class CopyingArrays {
    public static void main(String[] args) {

        int[] a = new int[7];
        int[] b = new int[10];
        Arrays.fill(a,33);
        Arrays.fill(b,55);
        /**
         * array copy
         * 这些参数啥意思都知道吧
         * 从a[0]开始拷贝 拷贝到b[0]位置
         * 拷贝多少个元素呢？拷贝a.length长度的元素
         */
        System.arraycopy(a,0, b,0, a.length);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        
    }
}
