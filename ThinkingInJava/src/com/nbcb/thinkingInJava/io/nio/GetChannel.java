package com.nbcb.thinkingInJava.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * ����ļ���Ҫ��Ϊ��˵�����ͨ��NIO��� FileChannel��д�ļ�
 *
 * ������Ҫ���һ�����⣬�����������������
 * 1.���д��ʱ����������
 * 2.�����ȡʱ����������
 */
public class GetChannel {
    public static void main(String[] args) {

        String filePath = "/Users/athena/Documents/delete/aout.txt";
        int BSIZE = 1024;

        try {
            // 1.��ͨ��FileChannel.write()д��һ�����ݵ������ļ�
            FileChannel fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("��� Hello Text ...".getBytes("UTF-8")));
            fc.close();

            // 2.��ͨ�����д��һ���µ����ݵ������ļ�
            fc = new RandomAccessFile(filePath,"rw").getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap("������ new content ...".getBytes("UTF-8")));
            fc.close();

            // 3.ͨ��FileChannel�ӱ����ļ���ȡ����
            fc = new FileInputStream(filePath).getChannel();
            // ����һ��ByteBuffer����FileChannel��ȡ����
            ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
            // decoder�������Ľ���
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            // ��ByteBufferתΪCharBuffer
            CharBuffer charBuffer = CharBuffer.allocate(BSIZE);
            while(fc.read(byteBuffer) != -1){
                byteBuffer.flip();
                // ͨ��decoder����ByteBufferתΪCharBuffer,ת�������У�����"UTF-8"���봦�������ַ�
                decoder.decode(byteBuffer,charBuffer,true);
                charBuffer.flip();
                while(charBuffer.hasRemaining()){
                    System.out.println((char) charBuffer.get());
                }
                byteBuffer.compact();
                // ���charBufferҲͬʱ�����byteBuffer ˵�������Ѿ�ͨ��buffer��ȡ�������
                charBuffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
