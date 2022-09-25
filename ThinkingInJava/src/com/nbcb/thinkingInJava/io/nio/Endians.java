package com.nbcb.thinkingInJava.io.nio;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * 展示ByteBuffer转为CharBuffer的时候，
 * 如何根据BIG_ENDIAN/LITTLE_ENDIAN
 */
public class Endians {
    public static void main(String[] args) {
        /**
         * 先创建一个长度为12个byte的ByteBuffer对象
         */
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        /**
         * 然后把ByteBuffer转为CharBuffer，并放入6个char: "abcdef"
         */
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
        /**
         * 以BIG_ENDIAN的方式，展示CharBuffer是如何布置byte顺序的
         * 备注：从右往左
         */
        bb.rewind();
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
        /**
         * 以LITTLE_ENDIAN的方式，展示CharBuffer是如何布置byte顺序的
         * 备注：从左往右
         */
        bb.rewind();
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));

    }
}
