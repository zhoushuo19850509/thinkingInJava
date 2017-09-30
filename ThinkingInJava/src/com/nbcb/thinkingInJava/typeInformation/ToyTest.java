package com.nbcb.thinkingInJava.typeInformation;

/**
 * 这个文件主要是说明Class类的一些信息，包括类名等
 * 有趣的是，能够获取该类实现的接口，该类的父类，等
 * 
 * @author 080776
 *
 */

interface Shoots{}
interface Waterproofs{}
interface HasBatteries{}

class Toy{
	Toy(){
		
	}
	Toy(int i){
		
	}
}

class FancyToy extends Toy implements Shoots,Waterproofs,HasBatteries{
	FancyToy(){
		super(1);
	}
}


public class ToyTest {
	static void printInfo(Class c){
		System.out.println("Class name: " + c.getName());
		System.out.println("is Interface: " + c.isInterface());
		System.out.println("Simple name: " + c.getSimpleName());
		System.out.println("CanonicalName: " + c.getCanonicalName());
	}
	
	public static void main(String[] args){
		try {
			Class cc = Class.forName("com.nbcb.thinkingInJava.typeInfomation.FancyToy");
			printInfo(cc);
			
			// 遍历cc实现的所有接口
			for(Class c: cc.getInterfaces()){
				printInfo(c);
			}
			
			// 获取父类
			Class up = cc.getSuperclass();
			printInfo(up);
			
			Class up1 = up.getSuperclass();
			printInfo(up1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
