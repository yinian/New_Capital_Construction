package com.liuwen.conditionalOnBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 20:33
 * @Description:
 */
@SpringBootApplication
public class SpringConditionalOnBeanByTypeApp implements CommandLineRunner {

    @Autowired
    private SpringServiceByType springServiceByType;

    public static void main(String[] args) {
        SpringApplication.run(SpringConditionalOnBeanByTypeApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Spring service fully qualified class name: " + springServiceByType.getClass());

    }
}
