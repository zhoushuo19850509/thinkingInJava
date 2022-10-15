package com.nbcb.thinkingInJava.strings.basic;

import com.nbcb.thinkingInJava.generics.inferfaces.Coffee;
import com.nbcb.thinkingInJava.generics.inferfaces.CoffeeGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子为了说明toString()方法
 * 例子中，我们直接打印了list对象
 * list对象因为是从Object类继承而来的，所以也会实现了toString()方法
 * List的toString()实现方式是遍历list中的各个元素，然后依次调用各个元素的toString()方法
 * 这里list中的元素是Coffee对象(Coffee的子类)，Coffee对象定义了自己的toString()方法
 */
public class ArrayListDisplay {
    public static void main(String[] args) {
        List<Coffee> list = new ArrayList<>();
        for (Coffee coffee : new CoffeeGenerator(10)) {
            list.add( coffee);
        }
        System.out.println(list);
    }
}
