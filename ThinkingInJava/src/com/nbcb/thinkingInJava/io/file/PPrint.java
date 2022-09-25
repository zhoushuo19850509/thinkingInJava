package com.nbcb.thinkingInJava.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

public class PPrint {

    public static String pformat(Collection<?> c){
        StringBuilder sb = new StringBuilder("[");
        System.out.println(c.size());
        for(Object obj : c){
            sb.append("\n");
            sb.append(obj);
        }
        sb.append("\n]");
        return sb.toString();
    }

    public static void main(String[] args) {
        File path = new File("src" +File.separator+
                "com"+ File.separator+"nbcb" + File.separator +
                "thinkingInJava" + File.separator +
                "io");  // 工程当前目录

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

//        for(String fileName : list){
//            System.out.println(fileName);
//        }
        System.out.println(pformat(Arrays.asList(list)));
    }


}
