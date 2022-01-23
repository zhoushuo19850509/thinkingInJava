package com.nbcb.thinkingInJava.annotations.junit;

import net.mindview.atunit.Test;
import net.mindview.atunit.TestObjectCreate;

/**
 * 这个文件主要是用于验证@TestObjectCreate注解
 * 当然，@TestObjectCreate这个注解是作者自己实现的。
 * JUnit中对一个的注解是@BeforeClass
 */
public class AtUnitExample3 {

    private int N;

    public AtUnitExample3(int n) {
        System.out.println("constructor1");
        this.N = n;
    }

    public AtUnitExample3() {
        System.out.println("constructor2");
    }

    public String methodOne(){
        return "this is method one";
    }

    public int methodTwo(){
        System.out.println("this is method two");
        return 2;
    }

    @TestObjectCreate
    public AtUnitExample3 create(){
        System.out.println("test object create");
        return new AtUnitExample3();
    }

    @Test
    public void methodOneTest(){
        assert N == 47;
    }



    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }
}
