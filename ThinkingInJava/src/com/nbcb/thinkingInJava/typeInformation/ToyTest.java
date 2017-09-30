package com.nbcb.thinkingInJava.typeInformation;

/**
 * ����ļ���Ҫ��˵��Class���һЩ��Ϣ������������
 * ��Ȥ���ǣ��ܹ���ȡ����ʵ�ֵĽӿڣ�����ĸ��࣬��
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
			
			// ����ccʵ�ֵ����нӿ�
			for(Class c: cc.getInterfaces()){
				printInfo(c);
			}
			
			// ��ȡ����
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
