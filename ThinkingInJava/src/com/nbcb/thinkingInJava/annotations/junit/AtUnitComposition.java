package com.nbcb.thinkingInJava.annotations.junit;

import org.junit.Test;

/**
 * 这个文件的意思和AtUnitExternalTest类似
 * 有时候我们要单元测试某个类，但是没有这个类的源码
 * 没法直接在这个类上写JUnit测试方法
 * 解决方案就是通过引入这个类，然后对这个类各个方法进行测试
 */
public class AtUnitComposition {
    AtUnitExample1 atUnitExample1 = new AtUnitExample1();

    @Test
    public void methodOneTest(){
        assert atUnitExample1.methodOne().equals("this is method one");
    }

    @Test
    public void methodTwoTest(){
        assert atUnitExample1.methodTwo() == 2;
    }

}
