package com.nbcb.thinkingInJava.enumeratedTypes.menu;

/**
 * 这里Food是一个接口，通过接口把enum进行了分类
 */
public interface Food {
    // 前菜
    enum Appetizer implements Food{
        SALAD, SOUP, SPRING_ROLLS;
    }
    // 主食
    enum MainCource implements Food{
        LASAGNE, BURRITO, PAD_THAI,
        LENTILS, HUMMOUS, VINDALOO;
    }
    // 甜点(Desert是沙漠...)
    enum Dessert implements Food{
        TIRAMISU, GELATO, BLACK_FOREST_CAKE,
        FRUIT, CREME_CARAMEL;
    }
    // 咖啡
    enum Coffee implements Food{
        BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
        LATTE, CAPPUCCINO, TEA, HERB_TEA;
    }
}
