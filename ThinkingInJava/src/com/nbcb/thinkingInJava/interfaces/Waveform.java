package com.nbcb.thinkingInJava.interfaces;


/*
 * ����һ���̳е�����
 * base class: Filter
 * Filter���ApplyTest.java�е�Processor�������Ƶķ���
 * ���ǣ�ȴ�޷�ͨ��Apply.process(Processor p,Object s)�������������
 * ����������Ϊ��˵���̳еľ���
 * */
public class Waveform {
	private static long counter;
	private final long id = counter++;  // ���id��¼��Waveform����ʵ���������� ��Waveform���� ÿʵ����һ�Σ�����+1
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








