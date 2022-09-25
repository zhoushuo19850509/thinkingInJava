package com.nbcb.thinkingInJava.io;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 这个代码说明了如何把多个文件打包成zip包，
 * 然后如何从zip包读取里面的各个原文件
 *
 * 后续可能需要关注：
 * 1.如果要压缩的文件正在使用，是否会中断打包；
 * 2.如何针对嵌套目录进行整体压缩
 * 3.如何处理解压后的文件和目录？
 * 4.如果解压之后的文件没法保存到本地，怎么办？
 * 总之，就要实现一下Linux环境zip/unzip shell的功能
 */
public class ZipCompress {
    public static void main(String[] args) {
        // 把各个文件压缩成这个zip文件
        String filePath = "/Users/athena/Documents/delete/abc.zip";
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            CheckedOutputStream checkedOutputStream =
                    new CheckedOutputStream(f, new Adler32());

            ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
            BufferedOutputStream out = new BufferedOutputStream(zipOutputStream);
            zipOutputStream.setComment("zip testing ...");

            // 然后遍历某个目录，把这个目录下所有的文件进行压缩
            String dirPath = "/Users/athena/Documents/delete/zip";
            File dir = new File(dirPath);
            for(File file: dir.listFiles()){
                String fileName = file.getName();
                System.out.println("start zip file : " + fileName);
                System.out.println(file.getAbsolutePath());
                BufferedReader in = new BufferedReader(new FileReader(
                        file.getAbsolutePath()
                ));
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                int c;
                while((c = in.read()) != -1){
                    out.write(c);
                }
                in.close();
                out.flush();
            }
            out.close();

            // 打印一下zip文件的checksum，只有压缩成功、zip文件关闭、通过checksum校验的才会打印这个
            System.out.println("checksum: " + checkedOutputStream.getChecksum().getValue());

            // 开始读取合并的zip文件
            System.out.println("start reading the zip file ...");

            FileInputStream fi = new FileInputStream(filePath);
            CheckedInputStream checkedInputStream = new CheckedInputStream(fi, new Adler32());
            ZipInputStream zipInputStream = new ZipInputStream(checkedInputStream);
            BufferedInputStream bis = new BufferedInputStream(zipInputStream);
            ZipEntry zipEntry;
            while ( (zipEntry = zipInputStream.getNextEntry()) != null){
                System.out.println("reading file >>>" + zipEntry.getName());
                int x;
                while((x = bis.read()) != -1){
                    System.out.write(x);
                }
                System.out.println("finish reading " + zipEntry.getName());
            }
            System.out.println("checksum: " + checkedInputStream.getChecksum().getValue());

            bis.close(); // 关闭stream

            // 另外一种方式读取zip文件 感觉更加简单一点 但是只能读取zip中的文件名
            // 如果要读取文件实际内容的话还是要用上面的方法
            System.out.println("start reading file by ZipFile way ...");
            ZipFile zipFile = new ZipFile(filePath);
            Enumeration e = zipFile.entries();
            while(e.hasMoreElements()){
                ZipEntry z = (ZipEntry)e.nextElement();
                System.out.println(z.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
