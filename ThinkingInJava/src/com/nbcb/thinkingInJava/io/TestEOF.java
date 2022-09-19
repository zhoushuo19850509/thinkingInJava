package com.nbcb.thinkingInJava.io;

import java.io.*;

/**
 * ͨ��DataInputStream.available()������ļ��Ƿ�ﵽ��ĩβ
 * available()������������ڼ��file input stream���������͵�stream�޷���֤�����Ƿ����
 *
 */
public class TestEOF {
    public static void main(String[] args) {
        String filePath = "/Users/zhoushuo/Documents/delete/a.txt";
        try {
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(filePath)));
            while(in.available() != 0){
                System.out.println((char) in.readByte());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
