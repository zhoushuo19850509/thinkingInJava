package com.nbcb.thinkingInJava.generics;


import com.nbcb.thinkingInJava.annotations.junit.AtUnit;

/**
 * Holder2
 * Holder2可以包含任意对象，对于对象的类型没有要求
 * 这当然比Holder1更加灵活，但是有一个问题，我们从Holder2中取出一个对象后
 * 不知道这个对象的类型
 */
public class Holder2 {
    private Object object;

    public Holder2(Object object) {
        this.object = object;
    }

    public Object get(){
        return this.object;
    }

    public void set(Object obj){
        this.object = obj;
    }

    public static void main(String[] args) {

        Holder2 holder2 = new Holder2(new Automobile());
        Automobile automobile = (Automobile) holder2.get();
        automobile.saySth();

        holder2.set("hello");
        String str  = (String) holder2.get();
        System.out.println("value of str : " + str);

        holder2.set(3);
        Integer integer = (Integer) holder2.get();
        System.out.println(integer);




    }

}
