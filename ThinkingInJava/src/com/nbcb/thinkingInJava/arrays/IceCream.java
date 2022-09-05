package com.nbcb.thinkingInJava.arrays;

import java.util.Arrays;
import java.util.Random;

/**
 * 这个代码主要是为了说明returning an array
 * function返回值是一个array
 *
 * 这个案例的场景非常实用，就是从一个长度为A的array中，取出n个元素(不能重复取，n < A)
 * 这个代码非常精简，但是实际运行起来，总是取一样的结果，不知道为啥
 */
public class IceCream {
    private static Random random = new Random(47);

    // 各种冰淇淋口味
    static final String[] FLAVORS = {"Chocolate", "Vanilla", "StrawBerry",
    "Classic","Mocha","Mud pie"};


    public static String[] flavorSet(int n){

        if(n > FLAVORS.length){
            System.out.println("to big set! ");
            return null;
        }

        String[] results = new String[n];

        // 备注：boolean array中每个元素默认都是false
        boolean[] picked = new boolean[FLAVORS.length];

        int t = 0;
        for (int i = 0; i < n; i++) {
            // 这个do/while循环的意思是从picked array中找一个之前每选过的元素
            do{
                t = random.nextInt(FLAVORS.length);
            }while(picked[t]);
//            System.out.println("t: " + t);
            results[i] = FLAVORS[t];
            picked[t] = true;
        }
        return results;

    }


    public static void main(String[] args) {
        int count = 15;
        for (int i = 0; i < count; i++) {
            String[] a = IceCream.flavorSet(5);
            System.out.println(Arrays.toString(a));
        }
    }
}
