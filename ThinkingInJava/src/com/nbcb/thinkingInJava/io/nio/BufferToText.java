package com.nbcb.thinkingInJava.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * 通过设置CharSet字符集编码，
 * 解决FileChannel读写文件时碰到的乱码问题
 */
public class BufferToText {

    public static void main(String[] args) {

        String filePath = "/Users/athena/Docume?nts/delete/aa.txt";
        int BSIZE= 1024;

        // 1.先通过FileChannel.write()写入一段内容到本地文件
        try {
            // 第一次 写入一些字符到某个文件
            FileChannel fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("Hello Text文本 ...".getBytes()));
            fc.close();

            // 然后从这个文件读取
            fc = new FileInputStream(filePath).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
            fc.read(byteBuffer);
            byteBuffer.flip();
            // 无论是中文还是英文，都会发现乱码
            System.out.println("第一次读取： " + byteBuffer.asCharBuffer());

            // 我们用本地的字符集编码，对ByteBuffer做一下解码，这样就解决了乱码问题
            byteBuffer.rewind();
            String encoding = System.getProperty("file.encoding");
            System.out.println("encoding: " + encoding);
            System.out.println("对buffer进行编码：" +
                    Charset.forName(encoding).decode(byteBuffer));

            // 第二次 尝试写入内容到本地文件
            fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("Hello Helen你好".getBytes("UTF-16BE")));
            fc.close();

            // 再次尝试读取文件
            fc = new FileInputStream(filePath).getChannel();
            byteBuffer.clear();
            fc.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("第二次读取： " + byteBuffer.asCharBuffer());

            // 第三次再次写入内容到本地文件
            fc = new FileOutputStream(filePath).getChannel();
            byteBuffer = ByteBuffer.allocate(30);
            byteBuffer.asCharBuffer().put("AAA苹果电脑");
            fc.write(byteBuffer);
            fc.close();

            fc = new FileInputStream(filePath).getChannel();
            byteBuffer.clear();
            fc.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("第三次读取： " + byteBuffer.asCharBuffer());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
