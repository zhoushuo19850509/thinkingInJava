package com.nbcb.thinkingInJava.io;

import java.nio.*;

/**
 * 这个文件啥意思明白吧
 *
 *
 */
public class ViewBuffer {
    public static void main(String[] args) {
        /**
         * 1.先创建一个ByteBuffer
         *   再展示这个ByteBuffer的内容
         *   这个ByteBuffer由8个byte组成
         */
        ByteBuffer bb = ByteBuffer.wrap(
                new byte[]{0,0,0,0,0,0,0,'a'}
        );
        bb.rewind();
        System.out.println();
        System.out.println("byte buffer ...");
        while(bb.hasRemaining()){
            System.out.print(bb.position() + " -> " + bb.get() + ". ");
        }

        /**
         * 2.ByteBuffer -> CharBuffer
         *   再展示这个CharBuffer的内容
         *   因为1个char包含2个byte，因此CharBuffer中，将2个byte合并成一个char
         */
        CharBuffer cb = ((ByteBuffer)bb.rewind()).asCharBuffer();
        System.out.println();
        System.out.println("char buffer ...");
        while(cb.hasRemaining()){
            System.out.print(cb.position() + " -> " + cb.get() + ". ");
        }

        /**
         * 3.ByteBuffer -> FloatBuffer
         *   再展示这个FloatBuffer的内容
         *   因为1个float包含4个byte，因此CharBuffer中，将4个byte合并成一个char
         */
        FloatBuffer fb = ((ByteBuffer)bb.rewind()).asFloatBuffer();
        System.out.println();
        System.out.println("FloatBuffer ...");
        while(fb.hasRemaining()){
            System.out.print(fb.position() + " -> " + fb.get() + ". ");
        }

        /**
         * 4.ByteBuffer -> IntBuffer
         *   再展示这个IntBuffer的内容
         *   因为1个int包含4个byte，因此IntBuffer中，将4个byte合并成一个int
         */
        IntBuffer ib = ((ByteBuffer)bb.rewind()).asIntBuffer();
        System.out.println();
        System.out.println("IntBuffer ...");
        while(ib.hasRemaining()){
            System.out.print(ib.position() + " -> " + ib.get() + ". ");
        }

        /**
         * 5.ByteBuffer -> LongBuffer
         *   再展示这个LongBuffer的内容
         *   因为1个long包含8个byte，因此LongBuffer中，将8个byte合并成一个long
         */
        LongBuffer lb = ((ByteBuffer)bb.rewind()).asLongBuffer();
        System.out.println();
        System.out.println("LongBuffer ...");
        while(lb.hasRemaining()){
            System.out.print(lb.position() + " -> " + lb.get() + ". ");
        }

        /**
         * 6.ByteBuffer -> ShortBuffer
         *   再展示这个ShortBuffer的内容
         *   因为1个short包含4个byte，因此ShortBuffer中，将2个byte合并成一个short
         */
        ShortBuffer sb = ((ByteBuffer)bb.rewind()).asShortBuffer();
        System.out.println();
        System.out.println("ShortBuffer ...");
        while(sb.hasRemaining()){
            System.out.print(sb.position() + " -> " + sb.get() + ". ");
        }

        /**
         * 7.ByteBuffer -> DoubleBuffer
         *   再展示这个DoubleBuffer的内容
         *   因为1个double包含8个byte，因此DoubleBuffer中，将8个byte合并成一个long
         */
        DoubleBuffer db = ((ByteBuffer)bb.rewind()).asDoubleBuffer();
        System.out.println();
        System.out.println("DoubleBuffer ...");
        while(db.hasRemaining()){
            System.out.print(db.position() + " -> " + db.get() + ". ");
        }




    }
}
