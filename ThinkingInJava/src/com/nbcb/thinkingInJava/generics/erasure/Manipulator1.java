package com.nbcb.thinkingInJava.generics.erasure;

public class Manipulator1<T> {
    private T obj;

    public Manipulator1(T obj) {
        this.obj = obj;
    }
    public void manipulate(){
//        obj.f(); // 这是不合法的哦
    }

}
