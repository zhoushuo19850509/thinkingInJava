package com.nbcb.thinkingInJava.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ���������˼Ҳ��ȷ
 * ͨ��FileChannel��ȡԴ�ļ�
 * ͨ��FileChannel������д��Ŀ���ļ�
 * ����ʵ�ʲ��ԣ�Ч�ʷǳ��ߣ���ַ�����channel io���ٶ�����
 * �������Աȶ�һ�º���ͨinputstream/outputstream�ĵĶ�д����
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
