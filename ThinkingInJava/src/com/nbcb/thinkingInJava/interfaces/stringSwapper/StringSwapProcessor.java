package com.nbcb.thinkingInJava.interfaces.stringSwapper;

import com.nbcb.thinkingInJava.interfaces.interfaceProcessor.*;;

/**
 * 
 * @author 080776
 * ����һ����ͨ���࣬�������һ�������� process() 
 * ��Ҫ����һ��input String�������String�е��ַ�����swap��
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
