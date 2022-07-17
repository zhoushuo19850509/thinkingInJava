package com.nbcb.thinkingInJava.generics.erasure;

import java.util.ArrayList;
import java.util.List;

public class FilledListMaker<T> {
    public List<T> create(T t, int n){
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < n; i++) {
             list.add(t);
        }
        return list;
    }

    public static void main(String[] args) {
        FilledListMaker<String> filledListMaker = new FilledListMaker<>();
        List<String> list = filledListMaker.create("Hello", 10);
        System.out.println(list);

    }

}
