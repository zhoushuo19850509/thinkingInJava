package com.nbcb.thinkingInJava.generics.mixins.basic;

public class Mixin
        extends BasicImpl
        implements SerialNumbered, TimeStamped{


    private SerialNumbered serialNumbered = new SerialNumberedImpl();
    private TimeStamped timeStamped = new TimeStampedImpl();

    @Override
    public long getSerialNumber() {
        return this.serialNumbered.getSerialNumber();
    }

    @Override
    public long getStamp() {
        return this.timeStamped.getStamp();
    }

}
