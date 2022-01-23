package com.nbcb.thinkingInJava.annotations.database;

/**
 * 这个java bean，就是映射到SQL DDL语句： 建表语句
 */
@DBTable(name="MEMBER")
public class Member {

    @SQLString(30)
    String firstName;

    @SQLString(50)
    String secondName;

    @SQLInteger(name = "myAge")
    int age;

    @SQLString(value = 32 ,
    constraints = @Constraints(allowNull = true,primaryKey = true)
    )
    String id;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
