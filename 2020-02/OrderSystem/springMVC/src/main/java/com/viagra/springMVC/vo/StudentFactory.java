package com.viagra.springMVC.vo;

/**
 * @Auther: viagra
 * @Date: 2020/2/25 20:56
 * @Description:
 */
public class StudentFactory {

    public static Student createStudent(){
        return new Student();
    }

    public static Student createStudent(String name,int age){
        return new Student(name,age);
    }

}
