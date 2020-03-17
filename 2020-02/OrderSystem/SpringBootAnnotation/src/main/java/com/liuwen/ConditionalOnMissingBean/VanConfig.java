package com.liuwen.ConditionalOnMissingBean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 09:14
 * @Description:
 */
@Configuration
public class VanConfig {

    @Bean
    @ConditionalOnBean(Billy.class)
    public Fighter fighter(){
        return new Billy();
    }

    @Bean
    @ConditionalOnMissingBean
    public Fighter fighter2(){
        return new Babana();
    }

}
