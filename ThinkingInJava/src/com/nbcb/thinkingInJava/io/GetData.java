package com.nbcb.thinkingInJava.io;

import java.nio.ByteBuffer;

/**
 * 这个代码重点关注ByteBuffer对象
 * 1.ByteBuffer初始化之后，默认值是啥
 * 2.如何往ByteBuffer读写各种数据类型
 *   (String/short int/int/long/float/double)等等
 */
public class GetData {

    static int BSIZE = 1024; // buffer size
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);

        /**
         * 1.首先看看ByteBuffer对象初始化之后，包含的内容是什么
         * 经过验证，包含的都是0
         */
        int i = 0;
        while( i++ < bb.limit()){
            if(bb.get() != 0){
                System.out.println("nonzero");
            }
        }
        System.out.println("index of i: " + i);

        /**
         * 对ByteBuffer对象进行重置，
         * 1.回到起始位置
         * 2.ByteBuffer所有内容清零
         */
        bb.rewind();

        /**
         * 2.往ByteBuffer写入String，然后逐个字符读取ByteBuffer内容
         */
        bb.asCharBuffer().put("Hello Mily");
        char c;
        while((c = bb.getChar()) != 0){
            System.out.print( c + " ");
        }
        System.out.println();
        bb.rewind();

        /**
         * 2.往ByteBuffer写入short int，然后一次性读取这个short int
         */
        bb.asShortBuffer().put((short)471142);
        System.out.println(bb.getShort());
        bb.rewind();


        /**
         * 3.ByteBuffer读写int
         */
        bb.asIntBuffer().put(923813145);
        System.out.println(bb.getInt());
        bb.rewind();

        /**
         * 4.ByteBuffer读写long
         */
        bb.asLongBuffer().put(923813145);
        System.out.println(bb.getLong());
        bb.rewind();


        /**
         * 5.ByteBuffer读写float
         */
        bb.asFloatBuffer().put(923813145);
        System.out.println(bb.getFloat());
        bb.rewind();

        /**
         * 6.ByteBuffer读写double
         */
        bb.asDoubleBuffer().put(923813145);
        System.out.println(bb.getDouble());
        bb.rewind();
    }


}
