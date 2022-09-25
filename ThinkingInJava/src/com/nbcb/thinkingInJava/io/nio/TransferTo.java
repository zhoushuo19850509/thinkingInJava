package com.nbcb.thinkingInJava.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过FileChannel transferTo()方法，快速把文件A复制到文件B
 *
 */
public class TransferTo {
    public static void main(String[] args) {
        String fileIn = "/Users/athena/Documents/delete/in.txt";
        String fileOut = "/Users/athena/Documents/delete/out.txt";
        try {
            FileChannel in = new FileInputStream(fileIn).getChannel();
            FileChannel out = new FileOutputStream(fileOut).getChannel();

            in.transferTo(0,in.size(),out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
