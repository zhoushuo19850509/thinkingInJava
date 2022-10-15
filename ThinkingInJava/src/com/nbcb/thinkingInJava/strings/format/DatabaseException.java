package com.nbcb.thinkingInJava.strings.format;

/**
 * 通过一个实例：自定义一个异常处理类
 * 来说明String.format()的用法
 */
public class DatabaseException extends Exception {
    public DatabaseException(int transactinId, int queryId, String message) {
        super(String.format("(t%d, q%d) message",transactinId, queryId, message));
    }

    public static void main(String[] args) {
        try{
            throw new DatabaseException(3, 6, "Write failed!");
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
