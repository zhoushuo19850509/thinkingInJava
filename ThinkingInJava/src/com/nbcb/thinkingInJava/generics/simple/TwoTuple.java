package com.nbcb.thinkingInJava.generics.simple;


/**
 * 这个类为了说明Generics对象，可以保持一定的顺序
 * @param <A>
 * @param <B>
 */
public class TwoTuple<A,B> {
    public A first;
    public B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "TwoTuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public static void main(String[] args) {
        TwoTuple<String, Integer> tuple =
                new TwoTuple<>("Hello",66);
        System.out.println(tuple);

    }
}
