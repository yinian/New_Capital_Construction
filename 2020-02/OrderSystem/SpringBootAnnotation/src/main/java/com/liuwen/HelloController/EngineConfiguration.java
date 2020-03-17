package com.liuwen.HelloController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 16:22
 * @Description:
 */
@Configuration
public class EngineConfiguration{
    @Bean
    public Engine getEngine() {
        return new Engine("Ferrari");
    }
}
