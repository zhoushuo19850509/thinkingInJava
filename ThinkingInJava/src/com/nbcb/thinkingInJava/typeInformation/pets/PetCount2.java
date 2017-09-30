package com.nbcb.thinkingInJava.typeInformation.pets;

public class PetCount2 {
	public static void main(String[] args){
		PetCreator creator = new LiteralPetCreator();
		PetCount.countPets(creator);
	}

}
