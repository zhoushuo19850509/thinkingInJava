package com.nbcb.thinkingInJava.generics.methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Generics 在methods中的应用场景2
 *
 * GenericVars的功能为，将array转化为list
 * 泛型的使用，使得这个功能最大限度做到了通用性
 *
 */
public class GenericVars {

    public static <T> List<T> makeList(T... args){

        List<T> list = new ArrayList<>();
        for(T t : args){
            list.add(t);
        }

        return list;
    }

    /**
     * 验证一下makeList()方法的功能
     * @param args
     */
    public static void main(String[] args) {

        List<String> list1 = GenericVars.makeList("A","B","C");
        System.out.println(list1);

        List<String> list2 = GenericVars.makeList("Hello,world...".split(""));
        System.out.println(list2);

    }


}
