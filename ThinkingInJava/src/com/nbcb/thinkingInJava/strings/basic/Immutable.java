package com.nbcb.thinkingInJava.strings.basic;

/**
 * 这个代码主要为了说明String对象immutable的性质
 * 意思就是我们对某个String对象操作，比如小写改为大写
 * 这个大写的String对象是新创建的对象，不是基于原来那个对象修改得来的。
 */
public class Immutable {
    public static void main(String[] args) {
        System.out.println("start string immutable...");
        String a = "abcdefg";
        System.out.println(a);
        String b = a.toUpperCase();
        System.out.println(b);

    }
}
