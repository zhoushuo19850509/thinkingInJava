package com.nbcb.thinkingInJava.strings.basic;

import java.util.Random;

/**
 * 使用StringBuilder打印一串随机数字
 */
public class UsingStringBuilder {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(47);
        sb.append("[");
        for (int i = 0; i < 25; i++) {
            sb.append(random.nextInt(100));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2 , sb.length());
        sb.append("]");
        System.out.println(sb.toString());
    }
}
