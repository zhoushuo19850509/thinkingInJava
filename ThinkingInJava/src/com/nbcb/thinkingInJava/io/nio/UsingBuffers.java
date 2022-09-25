package com.nbcb.thinkingInJava.io.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * 这个代码主要是为了说明CharBuffer的一些常用方法
 * reset()
 * mark()
 * rewind()
 *
 * 这些方法主要是用于处理CharBuffer中各个char内容
 * 这些方法应该是Buffer通用方法，对于其他类型的Buffer应该也是适用的
 */
public class UsingBuffers {

    /**
     * 遍历CharBuffer
     * 每次遍历的时候，都处理每两个char，把这两个char调换位置
     * @param charBuffer
     */
    public static void symetricScrable(CharBuffer charBuffer){
        while (charBuffer.hasRemaining()){
            /**
             * mark()的意思是把mark位置调整到position
             */
            charBuffer.mark();
            char c1 = charBuffer.get();
            char c2 = charBuffer.get();
            /**
             * reset()的意思是把当前postion位置调整到之前mark的位置
             */
            charBuffer.reset();
            charBuffer.put(c2);
            charBuffer.put(c1);
        }
    }

    public static void main(String[] args) {
        char[] data = "UsingBuffers".toCharArray();

        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        /**
         * 1.先往char buffer放一段char array
         * 然后打印一下原来的char buffer内容
         * 其中rewind()的意思是把buffer的postion调整到初始位置(position=0)
         * 同时把mark清空
         */
        cb.put(data);
        System.out.println(cb.rewind());
        /**
         * 调用symetricScrable()调换char位置
         * 然后打印char buffer
         */
        symetricScrable(cb);
        System.out.println(cb.rewind());
        /**
         * 再次调用symetricScrable()调换char位置
         * 这样char buffer又恢复到原来的内容了
         */
        symetricScrable(cb);
        System.out.println(cb.rewind());


    }
}
