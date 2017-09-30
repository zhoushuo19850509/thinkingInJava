package com.nbcb.thinkingInJava.containerInDepth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这个类是这章的开篇，为了说明： Collections.nCopies(arg0,arg1)这个方法
 * 能够批量new一组对象fill一个ArrayList 。
 * 值得注意的是，这样批量生成的ArrayList中的元素都指向同一个对象！
 * 当然，我们用另外一个方式就不一样了：每次都是new一个元素然后fill到list中，这样出来的元素都是唯一的对象。
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
		
		// fill List 方式1： Collections.nCopies(arg0,arg1)方法批量new一组对象fill一个ArrayList
		List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(4, new StringAddress("hello")));
		
		// fill List 方式2: 每次都new一个对象，然后add到list中去
//		List<StringAddress> list = new ArrayList<StringAddress>();
//		StringAddress sa1 = new StringAddress("hello");
//		StringAddress sa2 = new StringAddress("hi");
//		list.add(sa1);
//		list.add(sa2);
		System.out.println(list);
	}
}



