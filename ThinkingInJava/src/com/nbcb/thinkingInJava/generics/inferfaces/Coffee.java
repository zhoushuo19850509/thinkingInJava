package com.nbcb.thinkingInJava.generics.inferfaces;



public class Coffee {

    public static long counter = 0; // 全局变量
    public final long id = counter++;  // 标识对象id

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }

    public static void main(String[] args) {
        Coffee coffee = new Coffee();
        Coffee coffee1 = new Coffee();
        Coffee coffee2 = new Coffee();
        System.out.println(coffee);
        System.out.println(coffee1);
        System.out.println(coffee2);
    }
}
