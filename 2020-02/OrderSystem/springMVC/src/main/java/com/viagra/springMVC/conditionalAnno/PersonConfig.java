package com.viagra.springMVC.conditionalAnno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 14:00
 * @Description:
 */
@Configuration
public class PersonConfig {

    @Bean(name = "bill")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

    @Bean(name = "linus")
    public Person person2(){
        return new Person("Linus",48);
    }
}
