package com.nbcb.thinkingInJava.generics.inferfaces;


/**
 * 用生成斐波那契数列的例子，说明如何在interface中使用generics
 * 这里Fabonacci类模拟的就是斐波那契数列生成器，实现了Generator方法
 * next()就返回一个斐波那契数据
 */
public class Fibonacci implements Generator<Integer>{

    private int count = 0;  // 标识第几个斐波那契数

    private int fib(int n){
        if(n < 2){
            return 1;
        }
        return fib(n - 2) + fib(n - 1);
    }

    @Override
    public Integer next() {
        return fib(count++);
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci.next());
        }
    }
}
