package com.nbcb.thinkingInJava.io;

import java.io.*;

public class FileOutputShortcut {

    public static void main(String[] args) {
        String targetFilePath = "/Users/zhoushuo/Documents/delete/target.txt";
        String srcFilePath = "/Users/zhoushuo/Documents/delete/a.txt";

        // �ȶ�ȡԴ�ļ�
        try {
            BufferedReader in = new BufferedReader(
                    new StringReader(BufferedInputFile.read(srcFilePath)));
            /**
             * ���ж����PrintWriter�������������������
             */
//            PrintWriter out = new PrintWriter(
//                    new BufferedWriter(
//                            new FileWriter(targetFilePath)));
            /**
             * �������������������һ�����Ż���ͨ��OutputStreamWriter����һ���װ��
             * ��charset�������ı��룬ͨ��ʵ�ʲ��ԣ�������������������
             */
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(new File(targetFilePath)),"UTF-8")
                    ));
            int lineCount = 1;
            String s = "";
            while( (s = in.readLine()) != null ){
                out.println( lineCount++ + " : " + s);
            }
            out.close();
            System.out.println(BufferedInputFile.read(srcFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}