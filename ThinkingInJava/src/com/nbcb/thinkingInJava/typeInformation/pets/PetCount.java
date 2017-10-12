package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.HashMap;



public class PetCount {
	
	// PetCounter��������ľ�̬�࣬ͨ��һ��Map������¼��������Pet������
	static class PetCounter extends HashMap<String ,Integer>{
		public void count(String type){
			Integer quantity = get(type);
			if(quantity == null){
				put(type,1);
			}else{
				put(type,quantity + 1);
			}
		}
	}
	
	// �����̬������ͳ��PetCreator���ɵ�ʵ��
	public static void countPets(PetCreator creator){
		PetCounter counter = new PetCounter();
		for(Pet pet: creator.createArray(30)){
			System.out.println(pet.getClass().getSimpleName() + " ");
			if(pet instanceof Pet){
				counter.count("Pet");
			}
			if(pet instanceof Cat){
				counter.count("Cat");
			}
			if(pet instanceof Dog){
				counter.count("Dog");
			}
			if(pet instanceof Cymric){
				counter.count("Cymric");
			}
			if(pet instanceof EgyptianMau){
				counter.count("EgyptianMau");
			}
			if(pet instanceof Hamster){
				counter.count("Hamster");
			}
			if(pet instanceof Manx){
				counter.count("Manx");
			}
			if(pet instanceof Mouse){
				counter.count("Mouse");
			}
			if(pet instanceof Mutt){
				counter.count("Mutt");
			}
			if(pet instanceof Pug){
				counter.count("Pug");
			}
			if(pet instanceof Rat){
				counter.count("Rat");
			}
			if(pet instanceof Rodent){
				counter.count("Rodent");
			}
		}
		
		// ��ӡ���յĽṹ
		System.out.println(counter);
	}
	
	public static void main(String[] args){
		PetCreator creator = new ForNameCreator();
		PetCount.countPets(creator);
	}

}
