package com.nbcb.thinkingInJava.interfaces;

import java.util.Arrays;

/*
 * 这是一个继承的例子
 * base class: Processor
 * 
 * 另外，还有一个通用的入口类： Apply.process(Processor p,Object s)
 * */
public class Apply {
	
	public static String s = "Hello hob , welcome to Ningbo!";
	
	// 这个process方法比较通用，能够处理任何Processor类，以及Processor类的子类
	public static void process(Processor p,Object s){
		System.out.println("using processor : " + p.name());
		System.out.println(p.process(s));
	}
	
	public static void main(String[] args){
		
		Processor processor = new Processor();
		Upcast upcast = new Upcast();
		Downcast downcast = new Downcast();
		Splitter splitter = new Splitter();
		Apply.process(processor, s);
		Apply.process(upcast, s);
		Apply.process(downcast, s);
		Apply.process(splitter, s);
		
	}

}

// base class
class Processor{
	public String name(){
		return getClass().getSimpleName();
	}
	
	Object process(Object input){
		return input;
	}
}

// extends the base class 
// and cast the input string to uppercase
class Upcast extends Processor{
	String process(Object input){
		return ((String)input).toUpperCase();
	}	
}

//extends the base class 
//and cast the input string to lowercase
class Downcast extends Processor{
	String process(Object input){
		return ((String)input).toLowerCase();
	}	
}

//extends the base class 
//and split the input string to arrays
class Splitter extends Processor{
	String process(Object input){
		return Arrays.toString(((String)input).split(" "));
	}	
}
