package com.nbcb.thinkingInJava.io.compress;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * �������˵������ΰѶ���ļ������zip����
 * Ȼ����δ�zip����ȡ����ĸ���ԭ�ļ�
 *
 * ����������Ҫ��ע��
 * 1.���Ҫѹ�����ļ�����ʹ�ã��Ƿ���жϴ����
 * 2.������Ƕ��Ŀ¼��������ѹ��
 * 3.��δ����ѹ����ļ���Ŀ¼��
 * 4.�����ѹ֮����ļ�û�����浽���أ���ô�죿
 * ��֮����Ҫʵ��һ��Linux����zip/unzip shell�Ĺ���
 */
public class ZipCompress {
    public static void main(String[] args) {
        // �Ѹ����ļ�ѹ�������zip�ļ�
        String filePath = "/Users/athena/Documents/delete/abc.zip";
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            CheckedOutputStream checkedOutputStream =
                    new CheckedOutputStream(f, new Adler32());

            ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
            BufferedOutputStream out = new BufferedOutputStream(zipOutputStream);
            zipOutputStream.setComment("zip testing ...");

            // Ȼ�����ĳ��Ŀ¼�������Ŀ¼�����е��ļ�����ѹ��
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

            // ��ӡһ��zip�ļ���checksum��ֻ��ѹ���ɹ���zip�ļ��رա�ͨ��checksumУ��ĲŻ��ӡ���
            System.out.println("checksum: " + checkedOutputStream.getChecksum().getValue());

            // ��ʼ��ȡ�ϲ���zip�ļ�
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

            bis.close(); // �ر�stream

            // ����һ�ַ�ʽ��ȡzip�ļ� �о����Ӽ�һ�� ����ֻ�ܶ�ȡzip�е��ļ���
            // ���Ҫ��ȡ�ļ�ʵ�����ݵĻ�����Ҫ������ķ���
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
