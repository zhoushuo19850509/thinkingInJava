package com.nbcb.thinkingInJava.arrays;

import net.mindview.util.ConvertTo;
import net.mindview.util.Generated;
import net.mindview.util.Generator;
import net.mindview.util.RandomGenerator;

import java.util.Arrays;

/**
 * 这个文件说明如何用Arrays自带的排序、
 */
public class ArraySearching {

    public static void main(String[] args) {
        Generator<Integer> generator = new RandomGenerator.Integer(1000);

        // 先创建一个array
        int[] a = ConvertTo.primitive(Generated.array(new Integer[25], generator));


        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        // sort the array
        Arrays.sort(a);
        System.out.println("after sorted ...");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        while(true){
            int key = generator.next();
            int index = Arrays.binarySearch(a,key);
            if(index > 0){
                System.out.println("key : " + key);
                System.out.println("index: " + index);
                break;
            }
        }

    }

}
