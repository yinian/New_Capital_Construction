package com.viagra.springMVC.conditionalAnno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 14:00
 * @Description: 一个类中可以注入很多实例，@Conditional标注在类上就决定了一批bean是否注入
 */
@Conditional({WindowsCondition.class})
@Configuration
public class PersonConfig2 {

    @Bean(name = "bill")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

    @Bean(name = "linus")
    public Person person2(){
        return new Person("Linus",48);
    }
}
