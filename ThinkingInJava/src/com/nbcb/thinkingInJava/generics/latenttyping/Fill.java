package com.nbcb.thinkingInJava.generics.latenttyping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 这个代码的意思是这样的
 * Fill.fill()指定往某个集合类添加元素
 * 那么，我们只能往集合类型添加元素，如果不是集合类就不行
 * (比如我们例子中的SimpleQueue，SimpleQueue只实现了Iterable接口)
 */


/**
 * 这个类的作用是定义一个static方法fill()
 * 负责往某个集合类中添加某种类型的对象
 *
 */
public class Fill {
    public static <T> void fill(Collection<T> collection,
                                Class<? extends T> classToken, int size){
        try {
            for (int i = 0; i < size; i++) {
                collection.add(classToken.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}

/**
 * example class1 : base class
 */
class Contrast{
    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                '}';
    }
}

/**
 * example class2 : child class
 */
class TitleTransfer extends Contrast{}

class FillTest{
    public static void main(String[] args) {
        System.out.println("start fill test ...");

        List<Contrast> list = new ArrayList<Contrast>();
        Fill.fill(list, Contrast.class, 3);
        Fill.fill(list, TitleTransfer.class, 2);

        for(Contrast contrast : list){
            System.out.println(contrast);
        }

        /**
         * 以下的代码是不合法的，Fill.fill()只接收Collection类型
         * SimpleQueue虽然实现了Iterable接口，但是不属于Collection大家庭
         * 所以，Fill.fill没法处理
         */
//        SimpleQueue<Contrast> simpleQueue = new SimpleQueue<>();
//        Fill.fill(simpleQueue, Contrast.class, 10);

    }
}

