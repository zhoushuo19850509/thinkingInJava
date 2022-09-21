package com.nbcb.thinkingInJava.io;

import java.io.PrintWriter;

/**
 * change System.out -> PrintWriter
 */
public class ChangeSystemOut {
    public static void main(String[] args) {
        PrintWriter printWriter = new PrintWriter(System.out, true);
        printWriter.println("hello");
    }
}
