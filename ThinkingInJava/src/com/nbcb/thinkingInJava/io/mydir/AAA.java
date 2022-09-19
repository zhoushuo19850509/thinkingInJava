package com.nbcb.thinkingInJava.io.mydir;

import java.io.File;

public class AAA {
    public static void main(String[] args) {
        File file = new File("src" +File.separator+
                "com"+ File.separator+"nbcb" + File.separator +
                "thinkingInJava" + File.separator +
                "io"+ File.separator + "Directory.java");  // 工程当前目录
        System.out.println(file);
    }
}
