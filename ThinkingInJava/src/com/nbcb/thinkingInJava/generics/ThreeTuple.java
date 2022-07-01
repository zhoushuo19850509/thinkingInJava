package com.nbcb.thinkingInJava.generics;

public class ThreeTuple<A,B,C> extends TwoTuple<A,B> {

    public C third;

    public ThreeTuple(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public String toString() {
        return "ThreeTuple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }

    public static void main(String[] args) {
        ThreeTuple<String, Integer,Automobile> tuple =
                new ThreeTuple<>("Hello",66,new Automobile());
        System.out.println(tuple);


    }
}
