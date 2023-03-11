package com.nbcb.thinkingInJava.test;


import java.util.ArrayList;

class Employee{
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

class EmployeeList extends ArrayList<Employee> {

}


public class TemplateTest {
    public static void main(String[] args) {
        EmployeeList employees = new EmployeeList();
        employees.add(new Employee(1,"Hob"));
        employees.add(new Employee(2,"Mandy"));
        System.out.println(employees.get(1));
    }
}
