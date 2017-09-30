package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.ArrayList;
import java.util.List;

/**
 * ForNameCreator是一个实现类，继承了PetCreator抽象类
 * 具体实现了抽象类的方法是： types()
 * 在types中塞了很多Class对象
 * @author 080776
 *
 */
public class ForNameCreator extends PetCreator{
	
	private static List<Class<? extends Pet>> types = new ArrayList<Class<? extends Pet>>();
	
	private static String[] typeNames = {
		"com.nbcb.thinkingInJava.typeInformation.pets.Pet",
		"com.nbcb.thinkingInJava.typeInformation.pets.Cat",
		"com.nbcb.thinkingInJava.typeInformation.pets.Dog",
		"com.nbcb.thinkingInJava.typeInformation.pets.Pug",
		"com.nbcb.thinkingInJava.typeInformation.pets.Manx",
		"com.nbcb.thinkingInJava.typeInformation.pets.Mouse",
		"com.nbcb.thinkingInJava.typeInformation.pets.Rat",
		"com.nbcb.thinkingInJava.typeInformation.pets.Hamster",
		"com.nbcb.thinkingInJava.typeInformation.pets.Rodent",
		"com.nbcb.thinkingInJava.typeInformation.pets.EgyptianMau",
		"com.nbcb.thinkingInJava.typeInformation.pets.Cymric"	
	};

	private static void loader(){
		try {
			for(String typeName: typeNames){
				types.add( (Class<? extends Pet>)Class.forName(typeName));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static { loader(); };
	
	@Override
	public List<Class<? extends Pet>> types() {
		// TODO Auto-generated method stub
		return this.types;
	}
}
