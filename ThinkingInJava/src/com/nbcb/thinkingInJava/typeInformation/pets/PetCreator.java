package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * What does this abstract class do?
 * To collect the Classes under the packge of "typeInformation.pets" ,
 * and then create the corresponding instance of these classes
 * All the collected classes are pushed to the list of "types()"
 * 
 * Why the class of PetCreator is an abstract class?
 * PetCreator之所以是一个抽象，是因为types内容可以在子类中完成，因为types内容的生成有很多种方式。
 * 方式1 forName()   ForNameCreator  
 * 方式2 xxx.class   LiteralPetCreator
 * 
 * @author 080776
 *
 */
public abstract class PetCreator {
	
	private Random rand = new Random(47);
	
	public abstract List<Class<? extends Pet>> types();  // types这个List中包含了Pet的子类
	
	// 这个类随机产生一个Pet的子类
	public Pet randomPet(){
		int n = rand.nextInt(types().size());
		
		try {
			return types().get(n).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
		return null;		
	}
	
	public Pet[] createArray(int size){
		Pet[] result = new Pet[size];
		for(int i = 0 ; i < size ; i++){
			result[i] = randomPet();
		}
		return result;
	}
	
	public ArrayList<Pet> arrayList(int size){
		ArrayList<Pet> result = new ArrayList<Pet>();
		for(int i = 0 ; i < size ; i++){
			result.add(randomPet());
		}
		
		return result;
	}

}
