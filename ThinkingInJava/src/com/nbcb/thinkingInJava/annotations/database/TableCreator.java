package com.nbcb.thinkingInJava.annotations.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 这个类主要是将java bean转化为SQL DDL语句
 *
 * 处理步骤为：
 * 1.通过反射机制读取java bean
 * 2.解析java bean中的annotation
 * 3.根据annotation，将java bean中的字段转化为SQL DDL语句
 */
public class TableCreator {
    public static void main(String[] args) {
        Class<?> cl = Member.class;

        // 先获取表名
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        if(null == dbTable){
            System.out.println("表名未定义！");
            return ;
        }
        String tableName = dbTable.name();
        System.out.println("tableName: " + tableName);

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + tableName + " ( \n");

        // 再通过解析java bean的各个field，来获取SQL建表语句各个字段
        Field[] fields = cl.getDeclaredFields();
        for(Field field : fields){
            System.out.println(field.getName());
            String column = "";

            Annotation[] anns = field.getAnnotations();

            // 先过滤掉那些没有设置annotation的field
            if(null == anns || anns.length < 1){
                continue;
            }

            Annotation ann = anns[0];
            // 处理那些标记了@SQLString的字段(VARCHAR类型)
            if(ann instanceof SQLString){
                SQLString sString = (SQLString)ann;
                if(null == sString.name() || sString.name().length() < 1){
                    // 如果在annotation中没有指定特别的name，就直接用field name
                    column = field.getName();
                }else{
                    column = sString.name();
                }
                System.out.println("column of SQLString: " + column);
                sb.append(" " + column +
                          " VARCHAR(" + sString.value() + ") " +
                          " " + getConstraints(sString.constraints()) + ",\n");
            }

            // 再处理那些标记了@SQLInteger的字段(INT类型)
            if(ann instanceof SQLInteger){
                SQLInteger sInteger = (SQLInteger)ann;
                if(null == sInteger.name() || sInteger.name().length() < 1){
                    column = field.getName();
                }else{
                    column = sInteger.name();
                }
                System.out.println("column of SQLInteger: " + column);
                sb.append(" " + column + " INT " +
                          getConstraints(sInteger.constraints()) + ",\n");
            }
        }
        // 把最后一个字段后面的逗号替换掉
        int index = sb.lastIndexOf(",");
        sb.replace(index, index + 1 , "");
        sb.append(");");

        // 打印最终的结果
        String SQL = sb.toString();
        System.out.println(SQL);
    }

    /**
     * 根据我们之前定义的@Constraints annotation
     * 转化为SQL DDL语句
     *
     * 比如我们在java bean中定义的annotation:
     *  @SQLString(value = 32 , constraints = @Constraints(primaryKey = true))
     *  public String id;
     *
     *  就会转化为：
     *  id VARCHAR(32) PRIMARY KEY
     * @param constraints
     * @return
     */
    public static String getConstraints(Constraints constraints){
        String result = "";
        if(constraints.primaryKey()){
            result += "PRIMARY KEY";
        }
        if(!constraints.allowNull()){
            result += "NOT NULL";
        }
        if(constraints.unique()){
            result += "UNIQUE";
        }
        return result;
    }

}
