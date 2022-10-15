package com.nbcb.thinkingInJava.strings.format;

import java.util.Formatter;


/**
 * 介绍如何通过Formatter进行左右对齐
 * 1. %10s
 *   意思是这个String占据10个字符的长度，默认右对齐
 * 2. %-10s
 *   意思是这个String占据10个字符的长度，左对齐
 *
 * 后续如果碰到类似的场景，可以用这个代码
 */
public class Receipt {

    private double total = 0;
    private Formatter f = new Formatter(System.out);

    // 打印一行的头
    public void printTitle(){
        f.format("%-15s %5s %10s \n","Item","Qty","Price");
        f.format("%-15s %5s %10s \n","----","---","-----");
    }

    // 打印各个item的内容
    public void printItems(String itemName, int qty, double price){
        f.format("%-15s %5d %10.2f  \n",itemName, qty, price);
        total += price;
    }

    // 打印计算结果
    public void printTotal(){
        f.format("%-15s %5s %10s  \n","", "", "---");
        f.format("%-15s %5s %10.2f  \n","Total", "", total);
    }

    public static void main(String[] args) {
        Receipt receipt = new Receipt();
        receipt.printTitle();
        receipt.printItems("Beaf",2, 60.5);
        receipt.printItems("Closes",3, 215.0);
        receipt.printItems("Shoes",5, 560.5);
        receipt.printTotal();

    }


}
