package com.nbcb.thinkingInJava.io;

import java.io.*;

/**
 * 通过DataInputStream.available()来检测文件是否达到了末尾
 * available()这个方法仅限于检测file input stream，其他类型的stream无法保证功能是否可用
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
