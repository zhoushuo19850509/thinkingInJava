package com.nbcb.thinkingInJava.generics.mixins.decorate;

/**
 * Mixin的方式2
 * 我们把要获得的能力(比如Basic类的get()/set()方法)，放到装饰类中(Decorator)
 * 然后通过extend 装饰类的方式，获得这个能力
 */
public class Decoration {
    public static void main(String[] args) {


        SerialNumbered serialNumbered = new SerialNumbered(new Basic());
        serialNumbered.setValue("serial1 testing ...");
        System.out.println(serialNumbered.getValue() + " : " + serialNumbered.getSerialNumber());

        TimeStamped timeStamped = new TimeStamped(new Basic());
        timeStamped.setValue("timestamped testing ...");
        System.out.println(timeStamped.getValue() + " : " + timeStamped.getStamp());


    }
}
