package com.nbcb.thinkingInJava.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 这个代码Directory做了一件非常有意义的事情
 * 就是基于某个dir，以该dir为根节点
 * 遍历整个树形目录结构，把整个属性目录结构下所有的file/dir都放到TreeInfo中对象
 *
 * 我之前也实现过类似的功能，这个例子的代码远远要比我自己实现的先进
 * 代码更加精炼、功能更加强大(支持正则表达式)
 */
public class Directory {


    /**
     * FileInfo代表啥意思呢？就是代表以某个dir为根节点
     * 的树形目录架构(嵌套dir的)，这个架构包含整个下的所有文件和所有dir
     */
    public static class TreeInfo implements Iterable<File>{


        /**
         * 树形目录架构下所有的文件
         */
        public List<File> files = new ArrayList<>();
        /**
         * 树形目录架构下所有的目录
         */
        public List<File> dirs = new ArrayList<>();


        /**
         * FileInfo遍历什么呢？就是遍历以某个dir为根节点
         * 的整个嵌套目录架构下的所有文件(files)
         * @return
         */
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        /**
         * 将某个树形结构下所有的files/dirs添加到当前树形结构下
         * @param treeInfo
         */
        void addAll(TreeInfo treeInfo){
            files.addAll(treeInfo.files);
            dirs.addAll(treeInfo.dirs);
        }

        /**
         * toString()就是打印整个书目录下所有的
         * file names/dir names
         * @return
         */
        @Override
        public String toString() {
            return "TreeInfo{" +
                    "files=" + PPrint.pformat(files) +
                    ", \n\n dirs=" + PPrint.pformat(dirs)  +
                    '}';
        }
    }

    /**
     * 这个方法是整个Directory对象中最重要的，
     * 该方法的功能：返回一个TreeInfo嵌套的树形架构
     * 这个树形架构以baseFile为根节点，
     * 所有dir和所有符合regx的文件，都包含在返回的TreeInfo对象中
     * @param baseFile
     * @param regx
     * @return
     */
    public static TreeInfo recurseDirs(File baseFile, String regx){
        TreeInfo result = new TreeInfo();

        for(File item : baseFile.listFiles()){
            /**
             * 如果当前File是目录，先把当前目录放到树形架构中去
             * 然后嵌套遍历当前目录下的file/dir，把所有file/dir放到树形架构下
             */
            if(item.isDirectory()){
                result.dirs.add(item);
                result.addAll(recurseDirs(item, regx));
            }else{
                /**
                 * 如果当前File是文件，那么只要符合正则表达regx，就直接放到树形架构下
                 */
                if(item.getName().matches(regx)){
                    result.files.add(item);
                }
            }
        }
        return result;
    }

    /**
     * 下面三个walk重载方法就是基于recurseDirs，进行一系列封装
     *
     * @param startFile
     * @param regx
     * @return
     */
    public static TreeInfo walk(File startFile, String regx){
        return recurseDirs(startFile,regx);
    }

    public static TreeInfo walk(String startFilePath, String regx){
        return recurseDirs(new File(startFilePath),regx);
    }

    public static TreeInfo walk(File startFile){
        return recurseDirs(startFile,"*.");
    }


    public static void main(String[] args) {
        File path = new File("src" +File.separator+
                "com"+ File.separator+"nbcb" + File.separator +
                "thinkingInJava" + File.separator +
                "io");  // 工程当前目录
        System.out.println(walk(path,".*.java"));
    }


}
