package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

/*
 * 这个文件主要是为了说明，如何通过interface
 * 解决之前Apply.java和Waveform.java中，无法通过Apply.process(Processor p,Object s)统一调用各个子类的问题
 * */
public interface Processor {
	public String name();	
	Object process(Object input);
}



