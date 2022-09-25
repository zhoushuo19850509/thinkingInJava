package com.nbcb.thinkingInJava.io.standard;

import java.io.*;

/**
 * 这个代码取名Redirecting，什么意思知道的吧？
 * 其实就是延续了以前的Echo.java
 * 在Echo.java中，我们读取的inputstream是System.in，就是console input
 * 输出是System.out ，就是console out
 *
 * 但是，在这个代码中，我们把System.in重定向到某个文件
 * 然后把System.out 重定向到某个文件
 * 这样我们就能够通过in.readline()/ out.println把源文件内容输出到目标文件
 *
 * 在书本代码demo的基础上，我们做了一定的改进，读取源文件10行之后
 * 再把System.out替换回原来标准的console out
 *
 * 这里有2个关键点，
 * 1.要把原来标准console out保存起来：
 *   PrintStream console = System.out;
 * 2.后续把System.out替换回原来标准的console out之前，
 * 先要把我们修改后的file out stream关闭掉： out.close()
 *
 */
public class Redirecting {

    public static void main(String[] args) {
        PrintStream console = System.out;
        String inFile = "/Users/zhoushuo/Documents/delete/a.txt";
        String outFie = "/Users/zhoushuo/Documents/delete/out.data";

        try {

            // 从某个文件输出
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(inFile)
            );

            // 输出到某个文件
            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(outFie)
                    )
            );

            System.setIn(in);  // 输入改为从某个文件输出
            System.setOut(out); // 输出改为输出到某个文件
            System.setErr(out); // 异常信息输出到某个文件

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            String s = "";
            int lineCount = 0;
            while((s= br.readLine()) != null){
                System.out.println(s);

                if( ++lineCount > 10){
                    System.out.println("finish read lines from System.in(from file)");
                    out.close();
                    System.setOut(console);
                    break;
                }

            }

            while((s= br.readLine()) != null){
                System.out.println(s);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
