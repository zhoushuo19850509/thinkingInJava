package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

// 这里建立了一个真正的统一入口类
// 注意process(Processor p,Object s)方法中的Processor是一个接口
// 因此，只要实现了这个接口的类，就能通过Apply.process()方法调用
public class Apply {
	
	public static String s = "Hello hob , welcome to Ningbo!";
	
	// 这个process方法比较通用，能够处理任何Processor类，以及Processor类的子类
	public static void process(Processor p,Object s){
		System.out.println("using processor : " + p.name());
		System.out.println(p.process(s));
	}
	
	
	public static void main(String[] args){
		Apply.process(new Upcast(), s);
		Apply.process(new Downcast(), s);
		Apply.process(new Splitter(), s);
	}
	
	
}