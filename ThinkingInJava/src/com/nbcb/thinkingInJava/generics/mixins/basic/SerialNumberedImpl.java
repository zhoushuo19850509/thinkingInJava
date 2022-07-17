package com.nbcb.thinkingInJava.generics.mixins.basic;

public class SerialNumberedImpl implements SerialNumbered {

    private static long counter = 1;  // 全局id
    private final long id = counter++;  // 当前类所属的id


    @Override
    public long getSerialNumber() {
        return this.id;
    }
}
