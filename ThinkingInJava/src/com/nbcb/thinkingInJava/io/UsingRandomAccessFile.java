package com.nbcb.thinkingInJava.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UsingRandomAccessFile {

    static String filePath = "/Users/zhoushuo/Documents/delete/random.data";
    static File file = new File(filePath);

    /**
     * 通过RandomAccessFile展示某个文件的内容
     */
    public static void display(){
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "r");
            for (int i = 0; i < 7; i++) {
                System.out.println("value " + i + " : " + rf.readDouble());

            }
            System.out.println(rf.readUTF());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            /**
             * 往文件写入一些double/string
             */
            RandomAccessFile rf = new RandomAccessFile(file, "rw");
            for (int i = 0; i < 7; i++) {
                rf.writeDouble( i * 1.414);
            }
            rf.writeUTF("end of file");
            rf.close();
            display();

            rf = new RandomAccessFile(file, "rw");
            rf.seek(5 * 8);
            rf.writeDouble(47.0012);
            rf.close();
            display();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
