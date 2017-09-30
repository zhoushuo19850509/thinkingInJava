package com.nbcb.thinkingInJava.interfaces;


/*
 * 这是一个继承的例子
 * base class: Filter
 * Filter类和Apply.java中的Processor类有相似的方法
 * 但是，却无法通过Apply.process(Processor p,Object s)这个方法来调用
 * 因此这个例子为了说明继承的局限
 * */
public class Waveform {
	private static long counter;
	private final long id = counter++;  // 这个id记录了Waveform对象实例化的数量 ，Waveform对象 每实例化一次，计数+1
	public String toString(){
		return "Waveform " + id;
	}
	
	public static void main(String[] args){		
		for(int i = 0; i < 10 ;i++){
			Waveform waveform = new Waveform();
			System.out.println(waveform.toString());
		}		
	}
}








