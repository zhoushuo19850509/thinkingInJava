package com.nbcb.thinkingInJava.annotations;

public class Tesstable {
	
	public void excute(){
		System.out.println("Excuting..");
	}
	
	@Test
	void testExcute(){
		excute();
	}

}
