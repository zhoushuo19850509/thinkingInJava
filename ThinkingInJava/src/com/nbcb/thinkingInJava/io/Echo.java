package com.nbcb.thinkingInJava.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 通过BufferedReader读取System.in
 * 从而实现类似Linux下echo的功能
 */
public class Echo {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        String s = "";
        while(true){
            try {
                if ((s= br.readLine()) != null && s.length() != 0) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
