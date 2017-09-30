package com.nbcb.thinkingInJava.typeInformation;

import java.util.List;

import sun.reflect.generics.repository.ClassRepository;



public class ClassHierarchy {
	
	// ��ӡ�������class hierachy
	static void printClassHierachy(String className){
		// print the superclass/interface of the current class
		try {
			Class currentClass = Class.forName(className);	
			Class superClass = currentClass.getSuperclass();			
			System.out.println(superClass.getSimpleName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// print the chiled of the current class
		
	}
	
	// �ж��Ƿ��и���
	static boolean hasSuperClass(String className){
		Class currentClass;
		try {
			currentClass = Class.forName(className);
			Class superClass = currentClass.getSuperclass();
			if(null == superClass){
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}	
		return true;
	}
	
	// �ж��Ƿ��м̳нӿ�
	static boolean hasInterface(String className){
		Class currentClass;
		try {
			currentClass = Class.forName(className);
			Class[] interfaces = currentClass.getInterfaces();
			if(null == interfaces || interfaces.length == 0){
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}	
		return true;
	}

	/**
	 * �ж��Ƿ�������
	 * @param className һ������·�������������ƣ�java.util.ArrayList
	 * 
	 * ʵ��˼·
	 * Java û���ֳɵĽӿ����жϵ�ǰ�����Ƿ�������
	 * �۲���Σ���һ������·�������������ƣ�
	 * java.util.ArrayList  ����
	 * com.nbcb.thinkingInJava.typeInfomation.Toy
	 * ���ǿ��Ի�ȡ�������·���ĸ�Ŀ¼����������·���ĸ�·���ֱ�Ϊjava �� com
	 * Ȼ����������·�������е�package
	 */
	static boolean hasChild(String className){
		Class currentClass;
		try {
			currentClass = Class.forName(className);
			Class[] interfaces = currentClass.getInterfaces();
			if(null == interfaces || interfaces.length == 0){
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}	
		return true;
	}
	
	public static void main(String[] args){
//		ClassHierarchy.printClassHierachy("java.util.ArrayList");
//		ClassHierarchy.printClassHierachy("java.lang.Object");
		System.out.println(ClassHierarchy.hasSuperClass("java.util.ArrayList"));
		System.out.println(ClassHierarchy.hasSuperClass("java.lang.Object"));
		System.out.println(ClassHierarchy.hasInterface("java.util.ArrayList"));
		System.out.println(ClassHierarchy.hasInterface("com.nbcb.thinkingInJava.typeInfomation.Toy"));
		
		
	}

}
