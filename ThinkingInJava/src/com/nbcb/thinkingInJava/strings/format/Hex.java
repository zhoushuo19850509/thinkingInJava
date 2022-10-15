package com.nbcb.thinkingInJava.strings.format;

import net.mindview.util.BinaryFile;

import java.io.File;
import java.io.IOException;

/**
 * 这个代码通过String.format()的方式，
 * 将一个binary file按照16进制的格式进行输出
 * 这个代码的核心是format格式： "%02X "
 * 意思就是按照16进制输出，占据2个字符的位置，然后再空一格
 */
public class Hex {
    public static String format(byte[] data){


        StringBuilder result = new StringBuilder();
        int n = 0;
        for(byte b : data){
            if( n % 16 == 0){
                result.append(String.format("%05X",n));
            }
            result.append(String.format("%02X ",b));
            n++;
            if( n % 16 == 0){
                result.append("\n");
            }
        }

        return result.toString();

    }

    public static void main(String[] args) {
        String filepath = "/Users/zhoushuo/Documents/workspace/thinkingInJava/ThinkingInJava/src/com/nbcb/thinkingInJava/strings/basic/WithStringBuilder.class";
        File file = new File(filepath);
        try {
            byte[] data = BinaryFile.read(file);
            System.out.println(Hex.format(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
