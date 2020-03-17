package com.liuwen.EnableConfigurationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 15:12
 * @Description:
 */
@SpringBootApplication
@EnableConfigurationProperties({EnableConfigurationPropertiesDomain.class})  // 启动自定义的注解
public class SpringBootConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfigApplication.class,args);
    }
}
