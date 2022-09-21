package com.nbcb.thinkingInJava.io;

import net.mindview.util.OSExecuteException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 这个代码啥意思呢？就是通过java执行进程
 *
 * 可以实现：
 * 1.把进程执行过程中输出的正常内容通过inputstream进行读取，
 * 然后把读取的结果输出到某个out(本例是Sytem.out)
 * 后续完全可以改为输出到某个文件
 *
 * 2.把进程执行过程中输出的异常内容通过inputstream进行读取
 * 然后把异常输出到某个out(本例是Sytem.out)
 * 后续完全可以改为输出到某个异常日志文件
 *
 */
public class OSExecute {

    public static void command(String command){

        boolean err = false;

        try {
            Process process = new ProcessBuilder(
                    command.split(" ")).start();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String s;
            while( (s = br.readLine()) != null){
                System.out.println(s);
            }

            BufferedReader er = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );

            while( (s = er.readLine()) != null){
                System.out.println(s);
                err = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(err){
            throw  new OSExecuteException("Error execute process ...");
        }

    }


    public static void main(String[] args) {
        String commandLine = "ls /Users/zhoushuo/Documents/delete";
        command(commandLine);
    }

}
