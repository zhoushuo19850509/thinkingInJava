package com.nbcb.thinkingInJava.annotations.junit;

import org.junit.Test;

import java.util.HashSet;

/**
 * 这个文件主要是通过junit验证HashSet各个方法
 */
public class HashSetTest {

    HashSet<String> hashSet = new HashSet<>();

    @Test
    public void init(){
        assert hashSet.isEmpty();
    }

    @Test
    public void _contains(){
        hashSet.add("hello");

        assert hashSet.contains("hello");
    }

    @Test
    public void _init(){
        hashSet.add("hello");
        hashSet.remove("hello");
        assert hashSet.isEmpty();
    }
}
