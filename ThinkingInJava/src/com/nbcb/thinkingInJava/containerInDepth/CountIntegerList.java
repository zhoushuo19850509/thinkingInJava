package com.nbcb.thinkingInJava.containerInDepth;

import java.util.AbstractList;

/**
 * CountIntegerList�̳��˳�����AbstractList
 * ����ʵ�������������������󷽷��� get()/size()
 * �������������������ܹ���listһ��ʹ��AbstractList��
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
