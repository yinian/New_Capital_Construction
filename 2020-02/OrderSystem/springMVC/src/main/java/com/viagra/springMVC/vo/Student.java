package com.viagra.springMVC.vo;

/**
 * @Auther: viagra
 * @Date: 2020/2/25 20:49
 * @Description:
 */
public class Student {

    private String name;
    private int age;

    public Student() {
        super();
        System.out.println("无参构造方法");
    }

    public Student(String name, int age) {
        super();
        this.name = name;
        this.age = age;
        System.out.println("带参构造方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
