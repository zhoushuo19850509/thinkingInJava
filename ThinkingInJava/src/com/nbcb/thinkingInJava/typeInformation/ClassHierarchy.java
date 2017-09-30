package com.nbcb.thinkingInJava.typeInformation;

import java.util.List;

import sun.reflect.generics.repository.ClassRepository;



public class ClassHierarchy {
	
	// 打印类的整个class hierachy
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
	
	// 判断是否有父类
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
	
	// 判断是否有继承接口
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
	 * 判断是否有子类
	 * @param className 一个绝对路径的类名，类似：java.util.ArrayList
	 * 
	 * 实现思路
	 * Java 没有现成的接口来判断当前的类是否有子类
	 * 观察入参，是一个绝对路径的类名，类似：
	 * java.util.ArrayList  或者
	 * com.nbcb.thinkingInJava.typeInfomation.Toy
	 * 我们可以获取这个绝对路径的根目录，以上两个路径的根路径分别为java 和 com
	 * 然后遍历这个根路径下所有的package
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
