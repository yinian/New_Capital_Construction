package com.viagra.springMVC.vo;

/**
 * @Auther: viagra
 * @Date: 2020/2/25 20:56
 * @Description:
 */
public class StudentFactory2 {

    public static Student createStudentMeth(){
        return new Student();
    }

    public static Student createStudentMet(String name,int age){
        return new Student(name,age);
    }

}
