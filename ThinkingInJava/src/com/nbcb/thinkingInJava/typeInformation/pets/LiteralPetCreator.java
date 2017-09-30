package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiteralPetCreator extends PetCreator{
	
	// ��̬������ͨ��Literal�ķ�ʽ���ռ���com.nbcb.thinkingInJava.typeInformation.pets package
	// �����е�Class under Pet Hierarchy
	public static final List<Class<? extends Pet>> allTypes = 
		Collections.unmodifiableList(Arrays.asList(
				Pet.class,Dog.class,Cat.class,Rodent.class,
				Cymric.class,EgyptianMau.class,Hamster.class,
				Manx.class,Mouse.class,Mutt.class,Pug.class,Rat.class));
	
	// ֻͳ����Щ���࣬������Dog/Cat/Rodent/Pet��ͳ��
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
