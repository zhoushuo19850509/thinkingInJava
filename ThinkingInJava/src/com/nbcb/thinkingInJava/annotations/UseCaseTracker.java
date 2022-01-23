package com.nbcb.thinkingInJava.annotations;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UseCaseTracker {

    /**
     * 这个方法用来判断在某个类中遗漏了哪些usecase
     * @param ids ids是usecase id列表
     * @param cl 要检测的目标test case
     */
    public static void trackUseCases(List<Integer> ids, Class<?> cl){
        // 通过反射的机制，获取这个类中各个方法的annotation
        Method[] methods = cl.getDeclaredMethods();
        for(Method method : methods){
            Annotation annotation = method.getAnnotation(UseCase.class);
            int usecaseId = ((UseCase) annotation).id();
            if(ids.contains(usecaseId)){
                ids.remove(new Integer(usecaseId));
            }
        }
        // 把剩下的usecase id打印一下
        for(int leftId : ids){
            System.out.println("Warning: missing use case: " + leftId);
        }
    }

    public static void main(String[] args) {
        Method[] methods = PasswordUtil.class.getDeclaredMethods();
        for(Method method : methods){
            Annotation annotation = method.getAnnotation(UseCase.class);
            System.out.println(((UseCase) annotation).id());
            System.out.println(((UseCase) annotation).description());
        }

        List<Integer> ids = new ArrayList<>();
        ids.add(16);
        ids.add(29);
        ids.add(33);
        ids.add(95);
        UseCaseTracker.trackUseCases(ids, PasswordUtil.class);
    }
}
