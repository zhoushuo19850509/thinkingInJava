package com.nbcb.thinkingInJava.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * ͨ������CharSet�ַ������룬
 * ���FileChannel��д�ļ�ʱ��������������
 */
public class BufferToText {

    public static void main(String[] args) {

        String filePath = "/Users/athena/Docume?nts/delete/aa.txt";
        int BSIZE= 1024;

        // 1.��ͨ��FileChannel.write()д��һ�����ݵ������ļ�
        try {
            // ��һ�� д��һЩ�ַ���ĳ���ļ�
            FileChannel fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("Hello Text�ı� ...".getBytes()));
            fc.close();

            // Ȼ�������ļ���ȡ
            fc = new FileInputStream(filePath).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
            fc.read(byteBuffer);
            byteBuffer.flip();
            // ���������Ļ���Ӣ�ģ����ᷢ������
            System.out.println("��һ�ζ�ȡ�� " + byteBuffer.asCharBuffer());

            // �����ñ��ص��ַ������룬��ByteBuffer��һ�½��룬�����ͽ������������
            byteBuffer.rewind();
            String encoding = System.getProperty("file.encoding");
            System.out.println("encoding: " + encoding);
            System.out.println("��buffer���б��룺" +
                    Charset.forName(encoding).decode(byteBuffer));

            // �ڶ��� ����д�����ݵ������ļ�
            fc = new FileOutputStream(filePath).getChannel();
            fc.write(ByteBuffer.wrap("Hello Helen���".getBytes("UTF-16BE")));
            fc.close();

            // �ٴγ��Զ�ȡ�ļ�
            fc = new FileInputStream(filePath).getChannel();
            byteBuffer.clear();
            fc.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("�ڶ��ζ�ȡ�� " + byteBuffer.asCharBuffer());

            // �������ٴ�д�����ݵ������ļ�
            fc = new FileOutputStream(filePath).getChannel();
            byteBuffer = ByteBuffer.allocate(30);
            byteBuffer.asCharBuffer().put("AAAƻ������");
            fc.write(byteBuffer);
            fc.close();

            fc = new FileInputStream(filePath).getChannel();
            byteBuffer.clear();
            fc.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("�����ζ�ȡ�� " + byteBuffer.asCharBuffer());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
