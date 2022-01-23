package com.nbcb.thinkingInJava.annotations;

import org.junit.Test;

public class Tesstable {
	
	public void excute(){
		System.out.println("Excuting..");
	}
	
	@Test
	void testExcute(){
		excute();
	}

}
