package com.nbcb.thinkingInJava.interfaces.stringSwapper;

import com.nbcb.thinkingInJava.interfaces.interfaceProcessor.*;;

/**
 * 
 * @author 080776
 * 这是一个普通的类，这个类有一个方法： process() 
 * 主要处理一个input String，将这个String中的字符进行swap：
 * national -> lanoitan
 * 
 */
public class StringSwapProcessor {
	public String process(String input){
		
		return input.concat("_aaa");
	}
	
	public static void main(String[] args){
		StringSwapProcessor processor = new StringSwapProcessor();
//		System.out.println(processor.process("hello"));
		
		Apply.process(new StringSwapApater(processor), "hellome");
		
	}

}
