package com.nbcb.thinkingInJava.generics.latenttyping;


import com.nbcb.thinkingInJava.generics.inferfaces.Coffee;
import com.nbcb.thinkingInJava.generics.inferfaces.Generator;
import com.nbcb.thinkingInJava.generics.inferfaces.Latte;
import com.nbcb.thinkingInJava.generics.inferfaces.Mocha;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 这个代码要和之前Fill结合起来一起看
 * Fill.fill()中有一个限制，就是只能往Collection对象中塞元素
 * 但是，没法往SimpleQueue对象中塞元素，即便SimpleQueue对象中定义了add()也不行
 *
 * 这个代码就是解决这个问题的。
 * 怎么解决呢？运用了Adapter设计模式，通过一个Adapter类，将SimpleQueue转化为新的类
 * 新的类实现了Addable接口，Fill2.fill()往一个Addable实现类中添加元素
 *
 * 最终的效果我们可以体会一下，通过Adapter设计模式，将类A转化为类B
 * 类B中包含了我们想要调用的方法。这样就间接实现了latent typing的效果。
 *
 *
 */

/**
 * 定义adapter接口
 * @param <T>
 */
interface Addable<T>{
    void add(T t);
}

class Fill2{

    /**
     * fill方法1
     * 把对象实例放到Adadable对象中
     * @param addable
     * @param classToken
     * @param size
     * @param <T>
     */
    public static <T> void fill(Addable<T> addable,
                                Class<? extends T> classToken, int size){
        try {
            for (int i = 0; i < size; i++) {
                addable.add(classToken.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * fill方法2
     * 通过generator创建元素，并且把元素放到Addable对象中
     * @param addable
     * @param generator
     * @param size
     * @param <T>
     */
    public static <T> void fill(Addable<T> addable,
                                Generator<T> generator, int size){
        for (int i = 0; i < size; i++) {
            addable.add(generator.next());
        }
    }
}

class AddableCollectionAdapter<T> implements Addable<T>{

    private Collection<T> collection;

    public AddableCollectionAdapter(Collection<T> c) {
        this.collection = c;
    }

    @Override
    public void add(T t) {
        collection.add(t);
    }
}

/**
 * 这个类的功能是提供一个静态方法
 * 把一个集合对象封装一下，返回一个AddableCollectionAdapter
 *
 * @param <T>
 */
class Adapter<T>{

    public static <T> Addable collectionAdapter(Collection collection){
        return new AddableCollectionAdapter(collection);
    }
}

/**
 * 创建一个Adapter类
 * 通过Adaper设计模式，把原来的SimpleQueue封装一下
 * @param <T>
 */
class AddableSimpleQueue<T> extends SimpleQueue<T> implements Addable<T>{
    @Override
    public void add(T t) {
        super.add(t);
    }
}


public class Fill2Test {
    public static void main(String[] args) {
        List<Coffee> carrier = new ArrayList<Coffee>();
        Fill2.fill(new AddableCollectionAdapter(carrier),Coffee.class, 3);

        Fill2.fill(Adapter.collectionAdapter(carrier), Mocha.class,2);
        System.out.println(carrier);

        for(Coffee coffee : carrier){
            System.out.println(coffee);
        }

        System.out.println(">>>>>>>>>>>>>>>");
        AddableSimpleQueue<Coffee> addableSimpleQueue =
                new AddableSimpleQueue<Coffee>();
        Fill2.fill(addableSimpleQueue, Mocha.class,5);
        Fill2.fill(addableSimpleQueue, Latte.class, 6);

        for(Coffee coffee : addableSimpleQueue){
            System.out.println(coffee);
        }



    }
}
