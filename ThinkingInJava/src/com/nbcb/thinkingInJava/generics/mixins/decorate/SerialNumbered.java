package com.nbcb.thinkingInJava.generics.mixins.decorate;

public class SerialNumbered extends Decorator{

    private static long counter = 1;  // 全局id
    private final long serialNum = counter++;  // 当前类所属的id

    public SerialNumbered(Basic basic) {
        super(basic);
    }

    public long getSerialNumber() {
        return this.serialNum;
    }
}
