package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

/*
 * ����ļ���Ҫ��Ϊ��˵�������ͨ��interface
 * ���֮ǰApply.java��Waveform.java�У��޷�ͨ��Apply.process(Processor p,Object s)ͳһ���ø������������
 * */
public interface Processor {
	public String name();	
	Object process(Object input);
}



