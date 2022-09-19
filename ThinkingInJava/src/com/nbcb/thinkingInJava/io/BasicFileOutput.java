package com.nbcb.thinkingInJava.io;

import java.io.*;

/**
 * ����ļ�˵�����ͨ��output stream��һ���ļ������������һ���ļ�
 * �ر�ע���������й����У�������������������⣬ʵ��ʹ�õ�ʱ����Ҫ���һ��
 */
public class BasicFileOutput {

    public static void main(String[] args) {
        String targetFilePath = "/Users/athena/Documents/delete/target.txt";
        String srcFilePath = "/Users/athena/Documents/delete/a.txt";

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
