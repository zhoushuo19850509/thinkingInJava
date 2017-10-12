package com.nbcb.thinkingInJava.util;

public class TypeCounter extends HashMap<Class<?>,Integer>{
    private Class<?> baseType;

    // constructor
    public TypeCounter(Class<?> baseType){
        this.baseType = baseType;

    }

    // 这个方法专门用来统计实例的个数
    public void count(Object obj){
        Class<?> type = obj.getClass();
        if(!baseType.isAssignableFrom(type)){
            throw new RuntimeException(obj + " incorrect type");
        }
        countClass(type);
    }

    private void countClass(Class<?> type){
        Integer quantity = get(type);
        put(type,quantity == null ? 1: quantity + 1);
        Class<?> supeClass = type.getSuperclass();
        if(supeClass != null && baseType.isAssignableFrom(superClass)){
            countClass(superClass);
        }
    }

    public String toString(){
        StringBuilder result = new StringBuilder("{");
        for(Map.Entry<Class<?>,Integer> pair : entrySet()){
            result.append(pair.getKey().getSimpleName());
            result.append("=");
            result.append(pair.getValue());
            result.append(", ");

        }
        result.delete(result.length() - 2 , result.length());
        result.append("}");
        return result.toString();

    }

}
