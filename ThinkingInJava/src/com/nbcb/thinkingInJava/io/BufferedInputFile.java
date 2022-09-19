package com.nbcb.thinkingInJava.io;

import java.io.*;

/**
 * File -> String
 */
public class BufferedInputFile {
    public static String read(String filePath) throws IOException {
        /**
         * �ϵķ�ʽ�����������������
         */
//        BufferedReader in = new BufferedReader(new FileReader(filePath));
        /**
         * ͨ��BufferedReader����һ�㣬����������������
         */
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath),"UTF-8")
                );
        String s = "";
        StringBuilder sb = new StringBuilder();
        while( (s = in.readLine()) != null){
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String a = read("/Users/zhoushuo/Documents/delete/a.txt");
        System.out.println(a);
    }
}
