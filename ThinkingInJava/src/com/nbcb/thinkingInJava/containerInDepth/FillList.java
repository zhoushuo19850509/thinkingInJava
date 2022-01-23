package com.nbcb.thinkingInJava.containerInDepth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ����������µĿ�ƪ��Ϊ��˵���� Collections.nCopies(arg0,arg1)�������
 * �ܹ�����newһ�����fillһ��ArrayList ��
 * ֵ��ע����ǣ������������ɵ�ArrayList�е�Ԫ�ض�ָ��ͬһ������
 * ��Ȼ������������һ����ʽ�Ͳ�һ���ˣ�ÿ�ζ���newһ��Ԫ��Ȼ��fill��list�У�����������Ԫ�ض���Ψһ�Ķ���
 * 
 * @author zs
 *
 */
class StringAddress {
	
	private String s;
	
	public StringAddress(String sss){
		this.s = sss;
	}
	
	public String toString(){
		return super.toString() + "  " + this.s + "\n";
	}

}

public class FillList{
	public static void main(String args[]){
		
		// fill List ��ʽ1�� Collections.nCopies(arg0,arg1)��������newһ�����fillһ��ArrayList
		List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(4, new StringAddress("hello")));
		
		// fill List ��ʽ2: ÿ�ζ�newһ������Ȼ��add��list��ȥ
		List<StringAddress> list1 = new ArrayList<StringAddress>();
		StringAddress sa1 = new StringAddress("hi");
		StringAddress sa2 = new StringAddress("hi");
		list1.add(sa1);
		list1.add(sa2);
		System.out.println(list);

		System.out.println(list1);
	}
}



