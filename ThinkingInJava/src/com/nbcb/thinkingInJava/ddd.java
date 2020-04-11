package com.nbcb.thinkingInJava;

import java.util.ArrayList;
import java.util.List;

public class ddd {
    public static void main(String[] args){
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");

        for(int i = 0 ; i < a.size(); i++){
            System.out.println(a.get(i));
        }

        List<String> b = new ArrayList<String>();
        b.addAll(a);
        for(int i = 0 ; i <  b.size(); i++){
            System.out.println(b.get(i));
        }

        a.remove("1");
        a.add("4");

        for(int i = 0 ; i < a.size(); i++){
            System.out.println(a.get(i));
        }
        for(int i = 0 ; i <  b.size(); i++){
            System.out.println(b.get(i));
        }
    }
}
