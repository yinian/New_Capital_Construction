package com.liuwen.conditionalOnBean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 21:08
 * @Description:
 */
@Configuration
public class SpringConfigByName {
    @Bean
    @ConditionalOnBean(name = "anotherRequired")
    public SpringServiceByName springServiceByName() {
        return new SpringServiceByName();
    }
}
