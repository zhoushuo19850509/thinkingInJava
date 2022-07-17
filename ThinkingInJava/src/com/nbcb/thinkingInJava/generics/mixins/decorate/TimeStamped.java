package com.nbcb.thinkingInJava.generics.mixins.decorate;

import java.util.Date;

public class TimeStamped extends Decorator {
    private long timeStamp;

    public TimeStamped(Basic basic) {
        super(basic);
        this.timeStamp = new Date().getTime();
    }

    public long getStamp() {
        return this.timeStamp;
    }
}
