package com.nbcb.thinkingInJava.generics.mixins.decorate;

public class Decorator extends Basic {

    private Basic basic;

    public Decorator(Basic basic) {
        this.basic = basic;
    }

    @Override
    public String getValue() {
        return basic.getValue();
    }

    @Override
    public void setValue(String value) {
        basic.setValue(value);
    }
}
