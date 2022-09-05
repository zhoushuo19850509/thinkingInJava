package com.nbcb.thinkingInJava.arrays;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();

        a.add("A");
        a.add("B");
        a.add("C");

        System.out.println(a);

        String[] b = new String[5];
        a.toArray(b);
        System.out.println(b.length);
        System.out.println(b[0]);
        System.out.println(b[1]);

        System.out.println(b[2]);
        System.out.println(b[3]);


    }
}
