package com.nbcb.thinkingInJava.arrays;

import net.mindview.util.Generator;

import java.util.Random;

public class RandomGenerator {


    private static Random random = new Random(47);

    public static class Integer implements Generator<java.lang.Integer>{

        int mod = 10000;

        public Integer() {
        }

        public Integer(int mod) {
            this.mod = mod;
        }

        @Override
        public java.lang.Integer next() {
            return random.nextInt(this.mod);
        }
    }


    public static void main(String[] args) {
        Generator<java.lang.Integer> generator1 = new RandomGenerator.Integer();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator1.next());
        }
    }


}
