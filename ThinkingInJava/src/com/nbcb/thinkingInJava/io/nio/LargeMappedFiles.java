package com.nbcb.thinkingInJava.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过MappedByteBuffer快速写入一个大文件
 * 130ms左右就能够写入150MB的文件，且不会有OOM的风险
 */
public class LargeMappedFiles {
    static int length = 0x8FFFFFF;
    public static void main(String[] args) {
        String filePath = "/Users/zhoushuo/Documents/delete/large2.dat";
        try {
            long start = System.currentTimeMillis();
            MappedByteBuffer out = new RandomAccessFile(filePath,"rw").
                    getChannel().map(FileChannel.MapMode.READ_WRITE,0,length);
            for (int i = 0; i < length; i++) {
                out.put((byte) 'x');
            }
            System.out.println("finish writing ...");
            long end = System.currentTimeMillis();
            System.out.println("time cost: " + ( end - start ) + " ms");

            // 然后尝试从这个大文件中读取若干字符
            for (int i = length / 2; i < length / 2 + 6; i++) {
                System.out.println((char) out.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
