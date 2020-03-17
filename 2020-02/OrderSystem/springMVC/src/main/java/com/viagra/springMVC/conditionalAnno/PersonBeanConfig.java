package com.viagra.springMVC.conditionalAnno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 14:46
 * @Description:
 */
@Configuration
public class PersonBeanConfig {

    // 只有一个类时，大括号可以省略
    //如果WindowsCondition的实现方法返回true，则注入这个bean
    @Conditional({WindowsCondition.class})
    @Bean(name = "bill")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

    //如果LinuxCondition的实现方法返回true，则注入这个bean
    @Conditional({LinuxCondition.class})
    @Bean(name = "linus")
    public Person person2(){
        return new Person("Linus",48);
    }
}
