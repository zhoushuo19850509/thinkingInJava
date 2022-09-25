package com.nbcb.thinkingInJava.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 和之前的DirList2类似
 * 这里更进一步，采用匿名内部类的方式，精简了代码
 */
public class DirList3 {
    public static void main(String[] args) {
        File path = new File("src" +File.separator+
                "com"+ File.separator+"nbcb" + File.separator +
                "thinkingInJava" + File.separator +
                "userInterface");  // 工程当前目录

        String[] list ; // 保存文件名列表

        System.out.println(path.getAbsolutePath());
        String regx = ".*.java";

        list = path.list(new FilenameFilter() {

            private Pattern pattern = Pattern.compile(regx);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        });

        Arrays.sort(list,String.CASE_INSENSITIVE_ORDER); // sort the array

        for(String fileName : list){
            System.out.println(fileName);
        }
    }
}
