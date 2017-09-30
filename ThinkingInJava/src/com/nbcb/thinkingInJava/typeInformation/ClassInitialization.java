package com.nbcb.thinkingInJava.typeInformation;

import java.util.Random;

/**
 * 这个文件主要是为了说明 XXX.class和 Class.forName("XXX") 的区别
 * 两种方式都是获取Class的引用，但是有区别
 * 从程序运行结果来看，其中XXX.class 是lazy initialize
 *  Class.forName("XXX")是马上就初始化了
 * 
 * @author 080776
 *
 */
class Initable{
	static final int staticNonFinal = 47;
	static final int staticNonFinal2 = ClassInitialization.rand.nextInt(1000);
	static {
		System.out.println("Initializing Initable");
	}
}

class Initable2{
	static int staticNonFinal = 147;
	static {
		System.out.println("Initializing Initable2");
	}
}

class Initable3{
	static int staticNonFinal = 74;
	static {
		System.out.println("Initializing Initable3");
	}
}


public class ClassInitialization {
	public static Random rand = new Random(47);
	public static void main(String[] args) throws ClassNotFoundException{
		// Initable
		Class initable = Initable.class;
		System.out.println("after creating Initable ref");
		System.out.println(Initable.staticNonFinal);
		System.out.println(Initable.staticNonFinal2);
		
		// Initable2
		System.out.println(Initable2.staticNonFinal);
		
		// Initable3
		Class initable3 = Class.forName("com.nbcb.thinkingInJava.typeInfomation.Initable3"); 
		System.out.println("after creating Initable3 ref");
		System.out.println(Initable3.staticNonFinal);
	}

}
