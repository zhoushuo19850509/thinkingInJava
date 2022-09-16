package com.nbcb.thinkingInJava.arrays;

import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.Arrays;
import java.util.Random;

/**
 * 这个代码主要是试用Arrays.sort()方法，对array元素进行排序。
 * 那么按照什么逻辑进行排序呢？就是将数组中的元素实现Comparalbe接口
 * 在接口方法compare()定义元素排序逻辑
 * 另外，这里创建array demo的方式非常有意思，
 * 利用我们之前写的Generated.array()方法，快速创建了一个数组
 */
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

    @Override
    public String toString() {
        String result = "CompType{" +
                "i=" + i +
                ", j=" + j +
                '}';
        // 每三个元素回车一下，这样Arrays.toString打印数组的时候，排版更美观
        if( (count++ % 3) == 0){
            result += "\n";
        }
        return result;

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

        CompType[] compTypes = Generated.array(new CompType[12],generator());
        System.out.println("before sorting ...");
        System.out.println(Arrays.toString(compTypes));

        Arrays.sort(compTypes);
        System.out.println("after sorting ...");
        System.out.println(Arrays.toString(compTypes));

    }


}
