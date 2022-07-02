package com.nbcb.thinkingInJava.generics.simple;

/**
 * Generics在Stack数据结构中的使用
 * 备注：这里<T>代表Stack中某个节点中保存的元素类型
 * 比如LinkedStack<String>的意思是，这是一个String元素组成的栈
 *
 * 这个类展示了泛型在集合类中的重要作用：
 * 我们只有在声明一个新的集合类的时候，才会确定这个集合类中元素的类型
 *
 */
public class LinkedStack<T> {

    /**
     * 内部静态类，标识Stack中的一个节点
     */
    private static class Node<U>{
        U item;
        Node<U> next;

        public Node() {
            this.item = null;
            this.next = null;
        }

        public Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        /**
         * 当前节点是否为end node
         * (如果当前这个node为空，说明当前节点为end node)
         * @return
         */
        boolean isEnd(){
            return item == null && next == null;
        }
    }

    /**
     * Top node of the stack
     * top node初始时，包含一个null node
     */
    private Node<T> top = new Node<T>();

    /**
     * 从stack 顶部取一个元素
     * @return
     */
    public T pop(){

        T result = top.item;
        /**
         * if the top node is not the end node(null)
         * then ,we point the top node to the next of the top node
         */
        if(!top.isEnd()){
            top = top.next;
        }

        return result;
    }

    /**
     * create a new Node and
     * this new node becomes the top node of the stack
     * @param item
     */
    public void push(T item){
        top = new Node<T>(item, top);
    }


    /**
     * 我们在main方法中验证一下LinkedStack的功能
     * @param args
     */
    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<String>();
        String content = "Hello Hob, welcome the Disney World...";
        for(String str : content.split(" ")){
            stack.push(str);
        }

        String s = "";
        while( (s = stack.pop()) != null){
            System.out.println(s);
        }

    }
}
