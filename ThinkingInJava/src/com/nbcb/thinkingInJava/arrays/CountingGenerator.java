package com.nbcb.thinkingInJava.arrays;

import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

public class CountingGenerator {

    /**
     * 内部类，创建一系列的Boolean元素
     * 按顺序返回 true/false true/false
     */
    public static class Boolean implements Generator<java.lang.Boolean>{
        private boolean value = false;

        @Override
        public java.lang.Boolean next() {
            value = !value;
            return value;
        }
    }

    public static class Byte implements Generator<java.lang.Byte>{
        private byte value = 0;

        @Override
        public java.lang.Byte next() {
            return value++;
        }
    }

    static char[] CHARS = ("abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    public static class Character implements Generator<java.lang.Character>{
        private int index = -1;

        @Override
        public java.lang.Character next() {
            index = (index + 1) % CHARS.length;
            return CHARS[index];
        }
    }



    /**
     * 在main中验证我们CountingGenerator中各种Generator implementations
     * @param args
     */
    public static void main(String[] args) {
        Generator<java.lang.Boolean> generator1 = new CountingGenerator.Boolean();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator1.next());
        }

        Generator<java.lang.Byte> generator2 = new CountingGenerator.Byte();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator2.next());
        }

        Generator<java.lang.Character> generator3 = new CountingGenerator.Character();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator3.next());
        }

    }

}
