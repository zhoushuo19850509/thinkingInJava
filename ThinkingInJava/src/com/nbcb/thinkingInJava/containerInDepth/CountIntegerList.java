package com.nbcb.thinkingInJava.containerInDepth;

import java.util.AbstractList;

/**
 * CountIntegerList继承了抽象类AbstractList
 * 必须实现这个抽象类的两个抽象方法： get()/size()
 * 有了这两个方法，就能够像list一样使用AbstractList了
 */
public class CountIntegerList extends AbstractList<Integer> {
    private int size;

    public CountIntegerList(int size) {
        this.size = size < 0 ? 0 : size;
    }

    @Override
    public Integer get(int index) {
        return Integer.valueOf(index);
    }

    @Override
    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        CountIntegerList list = new CountIntegerList(30);
        System.out.println(list);
    }


}
