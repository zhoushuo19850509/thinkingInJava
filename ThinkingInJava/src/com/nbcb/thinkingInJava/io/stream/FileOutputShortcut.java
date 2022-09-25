package com.nbcb.thinkingInJava.io.stream;

import com.nbcb.thinkingInJava.io.file.BufferedInputFile;

import java.io.*;

public class FileOutputShortcut {

    public static void main(String[] args) {
        String targetFilePath = "/Users/zhoushuo/Documents/delete/target.txt";
        String srcFilePath = "/Users/zhoushuo/Documents/delete/a.txt";

        // 先读取源文件
        try {
            BufferedReader in = new BufferedReader(
                    new StringReader(BufferedInputFile.read(srcFilePath)));
            /**
             * 书中定义的PrintWriter，会有中文乱码的问题
             */
//            PrintWriter out = new PrintWriter(
//                    new BufferedWriter(
//                            new FileWriter(targetFilePath)));
            /**
             * 这里针对中文乱码作了一定的优化，通过OutputStreamWriter作了一层封装，
             * 对charset进行中文编码，通过实际测试，解决了中文乱码的问题
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
