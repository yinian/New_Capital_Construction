package com.viagra.springBoot.conditionalOnBean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 20:39
 * @Description:
 */
@Configuration
public class SpringConfig {

    @Bean
    @ConditionalOnBean(value = RequiredBean.class)
    public SpringService springService(){
        return new SpringService();
    }
}
