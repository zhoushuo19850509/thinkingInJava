package com.nbcb.thinkingInJava.annotations.junit;

import org.junit.Test;

/**
 * 这个文件的意思是，有时候我们要单元测试某个类，但是没有这个类的源码
 * 没法直接在这个类上写JUnit测试方法
 * 解决方案就是通过继承这个类，在子类中对父类(目标类)各个方法进行测试
 */
public class AtUnitExternalTest extends AtUnitExample1 {

    @Test
    public void methodOneTest(){
        assert methodOne().equals("this is method one");
    }

    @Test
    public void methodTwoTest(){
        assert methodTwo() == 2;
    }

}
