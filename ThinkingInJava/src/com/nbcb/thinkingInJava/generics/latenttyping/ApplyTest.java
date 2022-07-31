package com.nbcb.thinkingInJava.generics.latenttyping;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 通过反射的方式，解决latent typing 问题的第二种方式
 * 这次更进一步，通过反射的方式，指定要调用某个对象的某个方法
 *
 * 这个例子是这样的，有一个集合类，这个集合类中每个元素都是一个对象，
 * 然后遍历这个集合类，调用集合中每个元素某个指定的方法
 *
 * 泛型在这里又起到了重要作用：集合类中对象的类型是通用的，不必提前指定。
 */

class Apply {

    /**
     * 这个静态方法用到了泛型，是啥意思呢？
     * 就是传进来一个可遍历的集合类： seq
     * 然后遍历seq这个集合类中的各个元素
     * 然后调用每个元素的方法： f
     * @param seq
     * @param f
     * @param args
     * @param <T>
     * @param <S>
     */
    public static <T, S extends Iterable<? extends T>>
    void apply(S seq, Method f, Object... args){
        try {
            for(T t: seq){
                f.invoke(t, args);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}

class Shape{
    public void rotate(){
        System.out.println(this.getClass().getSimpleName() +
                " rotate ...");
    }

    public void resize(int newSize){
        System.out.println(this.getClass().getSimpleName() +
                " newSize: " + newSize);
    }
}

class Square extends Shape{

}

/**
 * 这个类的作用很明显哦，就是创建一个List
 * constructor中初始化List：
 * List中包含的元素个数为size
 * List中包含的元素类型为T(或者T的子类)
 * @param <T>
 */
class FillList <T> extends ArrayList<T> {
    public FillList(Class<? extends T> type, int size) {
        try {
            for (int i = 0; i < size; i++) {
                add(type.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


public class ApplyTest{

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println("start apply ...");


        System.out.println(">>>>>>>>>>>>>> start shapes ...");
        List<Shape> shapes = new ArrayList<Shape>();
        for (int i = 0; i < 10; i++) {
            shapes.add(new Shape());
        }
        Apply.apply(shapes, Shape.class.getMethod("rotate"));
        Apply.apply(shapes, Shape.class.getMethod("resize",int.class),5);

        System.out.println(">>>>>>>>>>>>>> start squares ...");
        List<Square> squares = new ArrayList<Square>();
        for (int i = 0; i < 10; i++) {
            squares.add(new Square());
        }
        Apply.apply(squares, Shape.class.getMethod("rotate"));
        Apply.apply(squares, Shape.class.getMethod("resize",int.class),5);

        System.out.println(">>>>>>>>>>>>>> start shapes generated by FillList ...");
        Apply.apply(new FillList<Shape>(Shape.class, 10),
                Shape.class.getMethod("rotate"));


        System.out.println(">>>>>>>>>>>>>> start SimpleQueue ...");
        SimpleQueue<Shape> shapeQ = new SimpleQueue<Shape>();
        for (int i = 0; i < 5; i++) {
            shapeQ.add(new Shape());
            shapeQ.add(new Square());
        }
        Apply.apply(shapeQ,
                Shape.class.getMethod("rotate"));

    }

}





