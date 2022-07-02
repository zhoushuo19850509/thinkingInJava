package com.nbcb.thinkingInJava.generics.inferfaces;


/**
 * 定义了一个interface
 * 这里用到了泛型，意思就是在interface中定义了一个方法
 * 这个方法的返回值是一个泛型
 * @param <T>
 */
public interface Generator<T> {
    T next();
}
