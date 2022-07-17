package com.nbcb.thinkingInJava.generics.erasure;

public class Manipulator2<T extends HasF> {
    private T obj;

    public Manipulator2(T obj) {
        this.obj = obj;
    }

    public void manipulate(){
        /**
         * 既然T是HasF的子类，那么调用Hasf的方法是合法的哦
         */
        obj.f();
    }

}
