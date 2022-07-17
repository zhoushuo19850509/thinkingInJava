package com.nbcb.thinkingInJava.generics.erasure;


import java.util.*;


/**
 * 这个类用来说明
 * 我们初始化一个对象之后，传递给这个类的type information，其实根本不存在
 * 代码中的getTypeParameters()方法，能够获取这个对象中的type parameter information
 * 通过代码实践可以看到，这个type parameter 其实啥内容都没有
 */

class Frob{}
class Fnorkle {}
class Quark<Q> {}
class Particle<POSITION, MOMENTUM> {}

public class LostInformation {

    public static void main(String[] args) {

        List<Frob> list = new ArrayList<Frob>();
        Map<Frob, Fnorkle> map = new HashMap<>();
        Quark<Frob> quark = new Quark<>();
        Particle<Long, Double> particle = new Particle<Long, Double>();

        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(particle.getClass().getTypeParameters()));

        System.out.println("finish ...");
    }


}
