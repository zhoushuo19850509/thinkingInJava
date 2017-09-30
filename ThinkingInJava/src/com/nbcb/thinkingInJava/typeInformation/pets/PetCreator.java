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
 * PetCreator֮������һ����������Ϊtypes���ݿ�������������ɣ���Ϊtypes���ݵ������кܶ��ַ�ʽ��
 * ��ʽ1 forName()   ForNameCreator  
 * ��ʽ2 xxx.class   LiteralPetCreator
 * 
 * @author 080776
 *
 */
public abstract class PetCreator {
	
	private Random rand = new Random(47);
	
	public abstract List<Class<? extends Pet>> types();  // types���List�а�����Pet������
	
	// ������������һ��Pet������
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
