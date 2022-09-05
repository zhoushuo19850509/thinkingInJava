package com.nbcb.thinkingInJava.arrays;


import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.Random;

/**
 * random object generator
 * 创建一个object array ,随机创建
 */
public class RandomGenerator {


    private static Random random = new Random(47);

    public static class Integer implements Generator<java.lang.Integer> {

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


    public static class RandomCharacter implements Generator<java.lang.Character>{

        @Override
        public java.lang.Character next() {
            int randomIndex = random.nextInt(CountingGenerator.CHARS.length);
            return CountingGenerator.CHARS[randomIndex];
        }
    }

    /**
     * RandomString这个类的作用是，创建随机String
     * 这个类比较有意思，代码非常简洁。啥意思呢？就是把我们在RandomGenerator中创建的
     * RandomCharacter替代父类(CountingGenerator.CountString)中的Char Generator
     * 子类可以访问父类的公共成员变量，这是一个非常好的例子
     */
    public static class RandomString extends CountingGenerator.CountString
     {
         {
             super.cg = new RandomGenerator.RandomCharacter();
         }

         public RandomString() {
         }

         public RandomString(int length) {
             super(length);
         }
     }


    public static void main(String[] args) {
        Generator<java.lang.Integer> generator1 = new RandomGenerator.Integer();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator1.next());
        }

        Generator<java.lang.Character> generator2 = new RandomGenerator.RandomCharacter();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator2.next());
        }

        Generator<java.lang.String> generator3 = new RandomGenerator.RandomString();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator3.next());
        }

    }


}
