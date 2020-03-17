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
public class SpringConditionalOnBeanByNameApp implements CommandLineRunner {

    @Autowired
    private SpringServiceByName springServiceByName;

    public static void main(String[] args) {
        SpringApplication.run(SpringConditionalOnBeanByNameApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Spring service fully qualified class name: " + springServiceByName.getClass());

    }
}
