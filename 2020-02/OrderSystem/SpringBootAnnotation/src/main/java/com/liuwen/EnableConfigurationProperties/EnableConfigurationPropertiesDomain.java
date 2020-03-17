package com.liuwen.EnableConfigurationProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Auther: viagra
 * @Date: 2020/2/27 15:35
 * @Description:
 */
//这里只有ConfigurationProperties注解
@ConfigurationProperties(prefix = "configuration.properties")
@Data
public class EnableConfigurationPropertiesDomain implements Serializable {


    private String name;
    //省略getter setter
    @Override
    public String toString() {
        return "EnableConfigurationPropertiesDomain{" +
                "name='" + name + '\'' +
                '}';
    }
}
