package com.nbcb.thinkingInJava.annotations.junit;

import org.junit.Test;

/**
 * 这个类，主要是验证JUnit的基本用法
 */
public class AtUnitExample1 {

    public String methodOne(){
        return "this is method one";
    }

    public int methodTwo(){
        System.out.println("this is method two");
        return 2;
    }

    @Test
    public void methodOneTest(){
        assert methodOne().equals("this is method one");
    }


    @Test
    public void methodTwoTest(){
        assert methodTwo() == 2;
    }



}
