package com.nbcb.thinkingInJava.annotations;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UseCaseTracker {

    /**
     * ������������ж���ĳ��������©����Щusecase
     * @param ids ids��usecase id�б�
     * @param cl Ҫ����Ŀ��test case
     */
    public static void trackUseCases(List<Integer> ids, Class<?> cl){
        // ͨ������Ļ��ƣ���ȡ������и���������annotation
        Method[] methods = cl.getDeclaredMethods();
        for(Method method : methods){
            Annotation annotation = method.getAnnotation(UseCase.class);
            int usecaseId = ((UseCase) annotation).id();
            if(ids.contains(usecaseId)){
                ids.remove(new Integer(usecaseId));
            }
        }
        // ��ʣ�µ�usecase id��ӡһ��
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
