package com.nbcb.thinkingInJava.generics.erasure;

import java.util.ArrayList;

/**
 * 通过比较两个class类，
 * 说明generice type information在对象中根本不存在哦
 *
 * 这个代码文件说明，虽然我们声明了两个Class对象
 * 声明的时候也指定了集合类中每个元素的类型
 * 但是，元素类型也仅仅只是声明了一下，
 * 通过实际代码运行可以发现，java把这个类型擦除了
 *
 */
public class ErasedTypeEquivalence {

    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();

        System.out.println(c1 == c2);

    }

}
