package com.nbcb.thinkingInJava.io.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public class FileLocking {

    private static String filePath =
            "/Users/zhoushuo/Documents/delete/a.txt";

    public static void main(String[] args) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            // 尝试锁定文件
            FileLock fl = fos.getChannel().tryLock();
            // 文件锁定
            if(fl != null){
                System.out.println("file locked ...");
                // 3秒钟后解锁该文件
                Thread.sleep(1000 * 3);
                fl.release();
                System.out.println("file released ...");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
