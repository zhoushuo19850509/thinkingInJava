package com.nbcb.thinkingInJava.io.compress;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ����ļ�����Ҫ�ǽ���
 * 1.��ΰ��ļ�ѹ����.gzѹ���ļ�
 * 2.Ȼ���ȡ.gz�ļ�����
 *
 * �ر�ע���ڶ�ȡ������Ҫ�ر�ע��������������
 */
public class GZIPcompress {
    public static void main(String[] args) {

        String srcFilePath = "/Users/athena/Documents/delete/aa.txt";
        String targetFilePath = "/Users/athena/Documents/delete/aa.gz";

        try {

            // �Ȱ�һ����ͨ�ļ�תΪһ��ѹ���ļ�

            /**
             * ��ע��Ϊ�˽���������������
             * ���������ڶ�ȡԴ�ļ���ʱ�򣬶��ļ�������һ�±���
             * ��Ҫ�Ƕ����һ��InputStreamReader������inputstream���ַ�������
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
                 * д��gzip�ļ���ʱ��Ҳ����Ҫ��д������ݽ���"UTF-8"���룬
                 * ������ܻ���������������
                 */
                out.write(s.getBytes("UTF-8"));
                out.write(13);
            }
            in.close();
            out.close();

            /**
             * Ȼ���ٶ�ȡ.gz�ļ�
             * ��ȡ.gz�ļ���ʱ��Ϊ�˽�������������⣬Ҳ��Ҫ���б��봦��
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
