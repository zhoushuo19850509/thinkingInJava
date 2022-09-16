package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;
import java.util.Comparator;

import static com.nbcb.thinkingInJava.arrays.CompType.generator;

/**
 * 这个代码也是沿袭CompType.java
 * 只不过这次是通过自定义的Comparator接口
 * 对数组CompType[]进行排序，排序逻辑是按照CompType.j进行升序
 */
public class CompTypeComparator implements Comparator<CompType> {

    @Override
    public int compare(CompType o1, CompType o2) {
//        return this.i < o.i ? -1:  (this.i == o.i ? 0 : 1);
        return o1.j < o2.j ? -1 : (o1.j == o2.j ? 0:1) ;
    }



    public static void main(String[] args) {
        CompType[] compTypes = Generated.array(new CompType[12],generator());
        System.out.println("before sorting ...");
        System.out.println(Arrays.toString(compTypes));

        Arrays.sort(compTypes,new CompTypeComparator());
        System.out.println("after sorting ...");
        System.out.println(Arrays.toString(compTypes));
    }

}
