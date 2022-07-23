package com.nbcb.thinkingInJava.generics.mixins.basic;

public class Mixins {

    public static void main(String[] args) {
        Mixin mixin1 = new Mixin();
        mixin1.setValue("mixin1 testing ...");

        Mixin mixin2 = new Mixin();
        mixin2.setValue("mixin2 testing ...");

        System.out.println(mixin1.getValue() + " " +
                mixin1.getSerialNumber() + " " +
                mixin1.getStamp());
        System.out.println(mixin2.getValue() + " " +
                mixin2.getSerialNumber() + " " +
                mixin2.getStamp());


    }

}
