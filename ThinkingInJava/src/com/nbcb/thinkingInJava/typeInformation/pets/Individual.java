package com.nbcb.thinkingInJava.typeInformation.pets;

public class Individual {
	private static long counter = 0;
	private final long id = counter++;
	
	private String name;
	
	public Individual(String name){
		this.name = name;
	} 
	
	public Individual(){
		
	} 
	
	public String toString(){
		return getClass().getSimpleName() +
		 (name == null ? "" : " " + name );
	}

}
