package com.viagra;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.viagra.wechatordering.pojo.mapper")
@EnableCaching
public class WechatOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatOrderingApplication.class, args);
	}

}
