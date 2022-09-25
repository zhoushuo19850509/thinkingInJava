package com.nbcb.thinkingInJava.io.stream;

import com.nbcb.thinkingInJava.io.file.BufferedInputFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 这个代码整合了之前的读取文件、写入文件的方法
 * 读取某个文件的内容，转化为String，
 * 然后把文件内容按照splitter进行分隔，
 * 分隔的各个String作为ArrayList的各个元素
 */
public class TextFile extends ArrayList<String> {

    /**
     * 通过BufferedReader，读取某个文件，转化为String
     * @param filePath
     * @return
     */
    public static String read(String filePath){
        String str = "";
        try {
            str = BufferedInputFile.read(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 把text写入到某个文件
     * @param filePath
     * @param text
     */
    public static void write(String filePath, String text){

        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(new File(filePath)),"UTF-8")
                    ));
            out.print(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }

    }


    /**
     * constructor
     * 意思就是通过我们之前定义的read()方法，读取某个文件
     * 然后把文件内容按照splitter进行分隔，
     * 分隔的各个String作为ArrayList的各个元素
     * @param filePath
     * @param splitter
     */
    public TextFile(String filePath, String splitter) {
        super(Arrays.asList(read(filePath).split(splitter)));
        if(get(0).equals("")){
            remove(0);
        }
    }

    /**
     * construtor2 默认按照"\n"分隔
     * @param filePath
     */
    public TextFile(String filePath){
        this(filePath, "\n");
    }

    public static void main(String[] args) {
        String filePath = "/Users/zhoushuo/Documents/delete/TextFile.java";
        String file = read(filePath);
        write("/Users/zhoushuo/Documents/delete/Result.java",file);
        System.out.println(file);

        System.out.println("start iterator through the ArrayList ...");
        /**
         * 将filePath文件内容读取出来之后，用非字母进行分隔
         * 经过分隔的各个元素，组成一个ArrayList
         */
        TextFile textFile = new TextFile(filePath, "\\W+");
        /**
         * TreeSet将ArrayList中的元素进行去重
         */
        TreeSet<String> treeSet =
                new TreeSet<>(new TextFile(filePath, "\\W+"));

        // 直接打印TreeSet中的各个元素
//        System.out.println(treeSet);
        /**
         * 直接打印TreeSet中的各个大写字母开头的元素，
         * headSet("a")啥意思知道吧？就是打印headSet中所有小于"a"的元素
         * 从ASCII可以知道，那基本就是大写字母了
         */
        System.out.println(treeSet.headSet("a"));

    }

}
