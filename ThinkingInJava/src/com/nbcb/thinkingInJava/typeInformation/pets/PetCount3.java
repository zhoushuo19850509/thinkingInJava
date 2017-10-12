package com.nbcb.thinkingInJava.typeInformation.pets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第三种方法来统计pet class hierarchy
 * 统计的方式为
 * 1.从LiteralPetCreator获取那些XX.class的集合
 * 2.然后新增一个count方法，通过isInstance()的方式，判断Class的类型
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
