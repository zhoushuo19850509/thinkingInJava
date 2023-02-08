package com.nbcb.thinkingInJava.enumeratedTypes.menu;

import net.mindview.util.Enums;

/**
 * Course基于Food.java接口
 * 啥意思呢？Course的用法参考Meal.java
 * Meal.java中的代码段，能够从前菜、主食、甜品、咖啡饮料
 * (APPETIZER/MAINCOURCE/DESSERT/COFFEE)依次随机取出一个食物
 * 最终组成一道完整的大餐
 */
public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURCE(Food.MainCource.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;

    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public Food randomSelection(){
        return Enums.random(values);
    }
}
