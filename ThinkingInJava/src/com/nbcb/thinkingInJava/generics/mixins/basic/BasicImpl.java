package com.nbcb.thinkingInJava.generics.mixins.basic;

public class BasicImpl implements Basic {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
