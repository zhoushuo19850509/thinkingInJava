package com.nbcb.thinkingInJava.strings.format;

import java.util.Formatter;

/**
 * 这个文件介绍如何通过Formatter类实现output format的效果
 */
public class Turtle {
    private String name;
    private Formatter f;

    /**
     * constructor
     * @param name
     * @param f
     */
    public Turtle(String name, Formatter f) {
        this.name = name;
        this.f = f;
    }

    public void move(int x, int y){
        f.format(" %s is moving at position : [%d , %d] \n",name, x,y);
    }

    public static void main(String[] args) {
        Turtle tommy = new Turtle("tommy", new Formatter(System.out));
        Turtle Larry = new Turtle("Larry", new Formatter(System.out));
        tommy.move(0,0);
        Larry.move(0,0);

        tommy.move(1,1);
        Larry.move(2,2);

        tommy.move(3,4);
        Larry.move(6,6);
    }

}
