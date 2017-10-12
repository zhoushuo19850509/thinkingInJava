package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * �����ַ�����ͳ��pet class hierarchy
 * ͳ�Ƶķ�ʽΪ
 * 1.��LiteralPetCreator��ȡ��ЩXX.class�ļ���
 * 2.Ȼ������һ��count������ͨ��isInstance()�ķ�ʽ���ж�Class������
 */
public class PetCount3 {
    static class PetCounter extends LinkedHashMap<Class<? extends Pet>,Integer> {

        // constructor of the subClass PetCounter
        // init the current linkedHashMap
        public PetCounter(){
//            super(MapData.map(LiteralPetCreator.allTypes,0));
            for(Class currentClass:LiteralPetCreator.allTypes){
                put(currentClass,0);
            }
        }

        // count the pet
        public void count(Pet pet){
            for(Map.Entry<Class<? extends Pet>,Integer> pair : entrySet()){
                if(pair.getKey().isInstance(pet)){
                    put(pair.getKey(),pair.getValue() + 1);
                }
            }
        }

        // print the resutl
        public String toString(){
            StringBuilder result = new StringBuilder("{");
            for(Map.Entry<Class<? extends Pet>,Integer> pair : entrySet()){
                result.append(pair.getKey().getSimpleName());
                result.append("=");
                result.append(pair.getValue());
                result.append(", ");
            }
            result.delete(result.length() - 2 , result.length());
            result.append("}");
            return result.toString();
        }
    }


    public static void main(String[] args){
        PetCounter counter = new PetCounter();
        StringBuilder petNames = new StringBuilder();
        for(Pet pet : Pets.createArray(20)){
            petNames.append(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println(petNames);
        System.out.println(counter);

    }
}
