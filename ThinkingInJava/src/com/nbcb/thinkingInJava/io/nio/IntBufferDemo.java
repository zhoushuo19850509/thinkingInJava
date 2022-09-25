package com.nbcb.thinkingInJava.io.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 这个代码啥意思呢？就是先初始化一个IntBuffer
 * 然后可以像操作数组一样，操作IntBuffer
 */
public class IntBufferDemo {
    static int BSIZE = 1024;
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = bb.asIntBuffer();
        ib.put(new int[]{11,22,33,44,55,66,77,88,99});
        // 可以像数组一样设置IntBuffer某个位置的内容
        ib.put(3,123);
        // 也可以数组一样获取IntBuffer某个位置的内容
        System.out.println(ib.get(4));


        /**
         * flip()方法的意思我们看这个方法的注释就知道了：
         * 1.把IntBuffer ib的大小设置为当前ib的实际大小
         * 2.把ib的当前位置指向到位置0(从头开始读取IntBuffer的内容)
         */
        ib.flip();
        System.out.println("start iterate through the IntBuffer ...");
        while(ib.hasRemaining()){
            int a = ib.get();
            System.out.println(a);
        }


    }
}
