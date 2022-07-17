package com.nbcb.thinkingInJava.generics.erasure;

public class Manipulation {
    public static void main(String[] args) {
        HasF hasF = new HasF();
        Manipulator1 manipulator1 = new Manipulator1(hasF);
        manipulator1.manipulate();

        Manipulator2 manipulator2 = new Manipulator2(hasF);
        manipulator2.manipulate();


    }
}
