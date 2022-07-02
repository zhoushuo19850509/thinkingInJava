package com.nbcb.thinkingInJava.generics.methods;

import com.nbcb.thinkingInJava.generics.inferfaces.Coffee;

/**
 * 之前我们在整个class的范围使用generic，用泛型代替了具体的某个class类
 * 这个小例子说明了generic在method中是怎么使用的，
 * 即把泛型类当做某个mothod的参数
 */
public class GenericMethods {
    public <T> void f(T t){
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethods genericMethods = new GenericMethods();
        genericMethods.f("hello");
        genericMethods.f(3);
        genericMethods.f(new Coffee());
    }


}
