package com.viagra;

import com.viagra.springMVC.beanAnno.HelloWorld;
import com.viagra.springMVC.beanAnno.HelloWorldConfig;
import com.viagra.springMVC.conditionalAnno.Person;
import com.viagra.springMVC.conditionalAnno.PersonBeanConfig;
import com.viagra.springMVC.conditionalAnno.PersonConfig;
import com.viagra.springMVC.conditionalAnno.PersonConfig2;
import com.viagra.springMVC.vo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student stu = (Student) context.getBean("student");
        System.out.println("姓名：" + stu.getName() + ";年龄：" + stu.getAge());
    }



    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student stu = (Student)context.getBean("student2");
        System.out.println("姓名："+stu.getName()+"	年龄："+stu.getAge());
        stu = (Student)context.getBean("studentArgs");
        System.out.println("姓名："+stu.getName()+"	年龄："+stu.getAge());
    }

    @Test
    public void test3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student stu = context.getBean("studentFactory",Student.class);
        System.out.println("姓名："+stu.getName()+"	年龄："+stu.getAge());
        stu = context.getBean("studentFactoryArgs",Student.class);
        System.out.println("姓名："+stu.getName()+"	年龄："+stu.getAge());
    }

    @Test
    public void testBeanAnno(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        helloWorld.setMessage("Hello World!");
        helloWorld.getMessage();
    }

    @Test
    public void testConditionalAnno(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersonConfig.class);

        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);


    }



    @Test
    public void testConditionalAnno2(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersonBeanConfig.class);
        String osName = applicationContext.getEnvironment().getProperty("os.name");
        System.out.println("当前系统为：" + osName);
        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);
    }

    @Test
    public void testConditionalAnno3(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersonConfig2.class);

        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);


    }








}
