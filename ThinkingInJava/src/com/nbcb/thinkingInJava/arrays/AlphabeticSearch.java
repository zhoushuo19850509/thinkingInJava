package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;

public class AlphabeticSearch {
    public static void main(String[] args) {

        String[] strings = Generated.array(new String[200000],
                new RandomGenerator.RandomString(5));

//        System.out.println("before sorting ...");
//        System.out.println(Arrays.toString(strings));

        Arrays.sort(strings,String.CASE_INSENSITIVE_ORDER);
//        System.out.println("after sorting asc...");
//        System.out.println(Arrays.toString(strings));

        System.out.println("start searching ...");
        int index = Arrays.binarySearch(strings, strings[155550],String.CASE_INSENSITIVE_ORDER);
        System.out.println("index: " + index);
        System.out.println("searched element: " + strings[155550]);

        String a1 = strings[1];
        System.out.println(a1);
        for(String str: strings){
            if(str.equals(a1)){
                System.out.println(str);
            }
        }


    }
}
