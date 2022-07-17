package com.nbcb.thinkingInJava.generics.mixins.basic;

import java.util.Date;

public class TimeStampedImpl implements TimeStamped {
    @Override
    public long getStamp() {
        return new Date().getTime();
    }
}
