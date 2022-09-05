package com.nbcb.thinkingInJava.arrays;

import com.nbcb.thinkingInJava.containerInDepth.CollectionData;
import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

/**
 * 这个类结合了genecic和CollectionData
 * 提供了两个static方法array，目标是通过generator，创建generic array
 *
 */
public class Generated {

    /**
     * 方法1 这个方法主要是借助CollectionData.java
     * 通过Generator创建generic object array
     * @param a
     * @param generator
     * @param <T>
     * @return
     */
    public static <T> T[] array(T[] a, Generator<T> generator){
        return new CollectionData<T>(generator, a.length).toArray(a);
    }

    /**
     * 方法2 重载了方法1 功能和方法1类似
     * 备注：这里
     * @param type
     * @param generator
     * @param size
     * @param <T>
     * @return
     */
    public static <T> T[] array(Class<T> type,
                                Generator<T> generator,
                                int size){
        // 先通过反射，初始化一个generic object array
        T[] a = (T[])java.lang.reflect.Array.newInstance(type, size);
        return new CollectionData<T>(generator, size).toArray(a);
    }

    /**
     * 通过main方法检验一下Generated.array()的功能
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 试用array()方法1
         */
        String[] a = new String[10];
        a = Generated.array(a,new RandomGenerator.RandomString());
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        /**
         * 试用array()方法2
         */
        System.out.println("start array() method2 ...");
        String[] b =
                Generated.array(String.class,new RandomGenerator.RandomString(),5);
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
    }


}
