package com.nbcb.thinkingInJava.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 这个代码意思也明确
 * 通过FileChannel读取源文件
 * 通过FileChannel把内容写入目标文件
 * 经过实际测试，效率非常高，充分发挥了channel io的速度优势
 * 后续可以比对一下和普通inputstream/outputstream的的读写性能
 */
public class ChannelCopy {
    public static void main(String[] args) {

        String fileIn = "/Users/athena/Documents/delete/in.txt";
        String fileOut = "/Users/athena/Documents/delete/out.txt";
        int BSIZE = 1024;
        try {
            FileChannel in = new FileInputStream(fileIn).getChannel();
            FileChannel out = new FileOutputStream(fileOut).getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
            while(in.read(buffer) != -1){
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
