package com.nbcb.thinkingInJava.io.stream;

import java.io.*;

public class StoringAndRestoringData {

    public static void main(String[] args) {
        String filePath = "/Users/zhoushuo/Documents/delete/data.txt";
        try {
            /**
             * 先通过DataOutputStream把各种内容写入某个文件
             */
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream((
                            new FileOutputStream(filePath)))
            );
            out.writeDouble(3.1415);
            out.writeUTF("That was PI");
            out.writeDouble(1.4143);
            out.writeUTF("Square root of 2");
            out.close();

            /**
             * 然后再从文件读取各个字段
             */
            DataInputStream in = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(filePath)
            ));
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
