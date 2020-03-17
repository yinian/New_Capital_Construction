package com.liuwen.HelloController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 16:24
 * @Description:
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(EngineConfiguration.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
