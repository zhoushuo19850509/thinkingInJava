package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiteralPetCreator extends PetCreator{
	
	// 静态常量，通过Literal的方式，收集了com.nbcb.thinkingInJava.typeInformation.pets package
	// 下所有的Class under Pet Hierarchy
	public static final List<Class<? extends Pet>> allTypes = 
		Collections.unmodifiableList(Arrays.asList(
				Pet.class,Dog.class,Cat.class,Rodent.class,
				Cymric.class,EgyptianMau.class,Hamster.class,
				Manx.class,Mouse.class,Mutt.class,Pug.class,Rat.class));
	
	// 只统计那些子类，抽象类Dog/Cat/Rodent/Pet不统计
	private static List<Class<? extends Pet>> types = allTypes.subList(allTypes.indexOf(Cymric.class),allTypes.size()); 
	
	public List<Class<? extends Pet>> types() {
		// TODO Auto-generated method stub
		return this.types;
	}
	
	public static void main(String[] args){
		PetCreator creator = new LiteralPetCreator();
		System.out.println(creator.types());
	}

}
