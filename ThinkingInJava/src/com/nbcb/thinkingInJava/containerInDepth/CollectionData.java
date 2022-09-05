package com.nbcb.thinkingInJava.containerInDepth;

import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.ArrayList;


/**
 * 这个类，主要是通过Generator创建一个generic array
 * @param <T>
 */
public class CollectionData<T> extends ArrayList<T> {

    public CollectionData(Generator<T> gen, int size) {
        for (int i = 0; i < size; i++) {
             add(gen.next());
        }
    }
}
