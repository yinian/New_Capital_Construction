package com.viagra.springMVC.beanAnno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 10:15
 * @Description:
 */
@Configuration
public class HelloWorldConfig {
    // 带有 @Bean 注解的方法名称作为 bean 的 ID，它创建并返回实际的 bean。也就是此时的bean
    // id为helloWorld，你的配置类可以声明多个 @Bean。
    // 一旦定义了配置类，你就可以使用 AnnotationConfigApplicationContext 来加载并把他们提供给 Spring 容器
    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
