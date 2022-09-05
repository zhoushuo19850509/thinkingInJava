package com.nbcb.thinkingInJava.arrays;

import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.Random;

public class CompType implements Comparable<CompType> {

    private static int count = 1;

    int i;
    int j;

    public CompType(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * 实现了Comparable<CompType>接口
     * 这里主要是比较属性i的大小，
     * @param o
     * @return
     */
    @Override
    public int compareTo(CompType o) {
        return this.i < o.i ? -1:  (this.i == o.i ? 0 : 1);
    }

    private static Random random = new Random(47);

    /**
     * generator负责创建CompType对象实例
     * @return
     */
    public static Generator<CompType> generator(){
        return new Generator<CompType>() {
            @Override
            public CompType next() {
                return new CompType(random.nextInt(100), random.nextInt(100));
            }
        };
    }

    public static void main(String[] args) {

//        CompType[] compTypes = Generated
    }


}
