package com.nbcb.thinkingInJava.strings.basic;

/**
 * 这个例子运行会造成堆栈溢出：  java.lang.StackOverflowError
 * 因为这个例子造成了无限循环：我们打印InfiniteRecursion对象的时候，
 * 会调用InfiniteRecursion对象的toString()方法打印
 * toString()方法又打印了对象本身。
 * 而打印对象本身又会调用toString()方法，这样就循环调用了。
 * 后续我们碰到StackOverflowError，要小心了，很有可能是无限循环引起的
 */
public class InfiniteRecursion {
    @Override
    public String toString() {
        return "InfiniteRecursion{}" + this + "\n";
    }

    public static void main(String[] args) {
        System.out.println(new InfiniteRecursion());
    }

}
