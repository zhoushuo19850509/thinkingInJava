package com.nbcb.thinkingInJava.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 和之前的DirList类似
 * 不过这里不是定义一个单独的FileNameFilter类
 * 而是通过内部类的形式，精简了代码
 */
public class DirList2 {
    /**
     * 定义一个static 方法，返回FilenameFilter实例
     * @param regx 正则表达式，比如.*.java
     * @return
     */
    public static FilenameFilter fileter(String regx){
        // 通过内部类的方式，创建一个FilenameFilter实例对象
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regx);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }

    public static void main(String[] args) {
        File path = new File("src" +File.separator+
                "com"+ File.separator+"nbcb" + File.separator +
                "thinkingInJava" + File.separator +
                "userInterface");  // 工程当前目录

        String[] list ; // 保存文件名列表

        System.out.println(path.getAbsolutePath());
        String regx = ".*.java";

        list = path.list(fileter(regx));

        Arrays.sort(list,String.CASE_INSENSITIVE_ORDER); // sort the array

        for(String fileName : list){
            System.out.println(fileName);
        }
    }


}
