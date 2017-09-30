package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

// ���ｨ����һ��������ͳһ�����
// ע��process(Processor p,Object s)�����е�Processor��һ���ӿ�
// ��ˣ�ֻҪʵ��������ӿڵ��࣬����ͨ��Apply.process()��������
public class Apply {
	
	public static String s = "Hello hob , welcome to Ningbo!";
	
	// ���process�����Ƚ�ͨ�ã��ܹ������κ�Processor�࣬�Լ�Processor�������
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