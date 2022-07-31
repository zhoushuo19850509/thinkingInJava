package com.nbcb.thinkingInJava.generics.latenttyping;

import java.util.Iterator;
import java.util.LinkedList;

public class SimpleQueue<T> implements Iterable<T> {

    private LinkedList<T> storage = new LinkedList<T>();

    public void add(T t){
        storage.offer(t);
    }

    public T get(){
        return storage.poll();
    }

    @Override
    public Iterator<T> iterator() {
        return storage.iterator();
    }

    public static void main(String[] args) {
        SimpleQueue<String> simpleQueue = new SimpleQueue<>();
        for (int i = 0; i < 10; i++) {
            simpleQueue.add(Integer.toString(i));
        }

        for(String str : simpleQueue){
            System.out.println(str);
        }
    }

}
