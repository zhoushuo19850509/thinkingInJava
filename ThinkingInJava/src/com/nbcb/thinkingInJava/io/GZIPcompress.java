package com.nbcb.thinkingInJava.io;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 这个文件，主要是介绍
 * 1.如何把文件压缩成.gz压缩文件
 * 2.然后读取.gz文件内容
 *
 * 特别备注：在读取过程中要特别注意中文乱码问题
 */
public class GZIPcompress {
    public static void main(String[] args) {

        String srcFilePath = "/Users/athena/Documents/delete/aa.txt";
        String targetFilePath = "/Users/athena/Documents/delete/aa.gz";

        try {

            // 先把一个普通文件转为一个压缩文件

            /**
             * 备注：为了解决中文乱码的问题
             * 这里我们在读取源文件的时候，对文件内容做一下编码
             * 主要是多加了一层InputStreamReader，设置inputstream的字符集编码
             */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(srcFilePath),"UTF-8")
            );
            BufferedOutputStream out = new BufferedOutputStream(
                    new GZIPOutputStream(
                            new FileOutputStream(targetFilePath)
                    )
            );
            System.out.println("start creating gz file ...");
            String s = "";
            while((s = in.readLine()) != null){
                /**
                 * 写入gzip文件的时候，也必须要对写入的内容进行"UTF-8"编码，
                 * 否则可能会与中文乱码问题
                 */
                out.write(s.getBytes("UTF-8"));
                out.write(13);
            }
            in.close();
            out.close();

            /**
             * 然后再读取.gz文件
             * 读取.gz文件的时候，为了解决中文乱码问题，也需要进行编码处理
             */
            System.out.println("start reading from the gz file ...");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new GZIPInputStream(
                            new FileInputStream(targetFilePath)
                    ),"UTF-8")
            );
            String str;
            while( (str = bufferedReader.readLine()) != null){
                System.out.println(str);
            }
            System.out.println("finish reading from the gz file...");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
