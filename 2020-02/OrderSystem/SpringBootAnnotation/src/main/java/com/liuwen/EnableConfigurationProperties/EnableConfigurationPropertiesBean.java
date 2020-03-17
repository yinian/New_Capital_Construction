package com.liuwen.EnableConfigurationProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 15:35
 * @Description:
 */
@Component
@EnableConfigurationProperties(value = EnableConfigurationPropertiesDomain.class)
public class EnableConfigurationPropertiesBean {
}
