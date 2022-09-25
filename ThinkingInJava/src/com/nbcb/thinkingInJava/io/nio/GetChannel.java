package com.nbcb.thinkingInJava.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 这个文件主要是为了说明如何通过NIO组件 FileChannel读写文件
 *
 * 这里需要解决一个问题，就是中文乱码的问题
 * 1.解决写入时的中文问题
 * 2.解决读取时的中文问题
 */
public class GetChannel {
    public static void main(String[] args) {

        String filePath = "/Users/athena/Documents/delete/aout.txt";
        int BSIZE = 1024;

        try {
            // 1.先通过FileChannel.write()写入一段内容到本地文件
            FileChannel fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("你好 Hello Text ...".getBytes("UTF-8")));
            fc.close();

            // 2.再通过随机写入一段新的内容到本地文件
            fc = new RandomAccessFile(filePath,"rw").getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap("新内容 new content ...".getBytes("UTF-8")));
            fc.close();

            // 3.通过FileChannel从本地文件读取内容
            fc = new FileInputStream(filePath).getChannel();
            // 定义一个ByteBuffer，从FileChannel读取内容
            ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
            // decoder用于中文解码
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            // 将ByteBuffer转为CharBuffer
            CharBuffer charBuffer = CharBuffer.allocate(BSIZE);
            while(fc.read(byteBuffer) != -1){
                byteBuffer.flip();
                // 通过decoder，将ByteBuffer转为CharBuffer,转化过程中，按照"UTF-8"编码处理中文字符
                decoder.decode(byteBuffer,charBuffer,true);
                charBuffer.flip();
                while(charBuffer.hasRemaining()){
                    System.out.println((char) charBuffer.get());
                }
                byteBuffer.compact();
                // 清空charBuffer也同时清空了byteBuffer 说明我们已经通过buffer读取内容完毕
                charBuffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
