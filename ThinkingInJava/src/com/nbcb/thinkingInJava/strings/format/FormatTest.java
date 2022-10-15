package com.nbcb.thinkingInJava.strings.format;

import java.util.Formatter;

/**
 * 介绍如何通过Formatter进行左右对齐
 * 1. %10s
 *   意思是这个String占据10个字符的长度，默认右对齐
 * 2. %-10s
 *   意思是这个String占据10个字符的长度，左对齐
 */
public class FormatTest {
    public static void main(String[] args) {
        Formatter f = new Formatter(System.out);
        f.format("%10s \n","hello");
        f.format("%10s \n","hello123");
        f.format("%-10s \n","hello999");

    }
}
