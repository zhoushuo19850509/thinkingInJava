package com.nbcb.thinkingInJava.io;

import java.io.*;

/**
 * File -> String -> StringReader -> chars
 */
public class MemoryInput {
    public static void main(String[] args) {
        try {
            String filePath = "/Users/zhoushuo/Documents/delete/a.txt";
            StringReader in = new StringReader(BufferedInputFile.read(filePath));
            int c;
            while((c = in.read()) != -1){
                System.out.println((char)c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
