package com.liuwen.ConditionalOnMissingBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 09:12
 * @Description:
 */
@SpringBootApplication
public class ConditionalOnMissingBeanApplication implements CommandLineRunner {

    @Autowired
    private Van van;

    public static void main(String[] args) {
        SpringApplication.run(ConditionalOnMissingBeanApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        van.fight();
    }
}
