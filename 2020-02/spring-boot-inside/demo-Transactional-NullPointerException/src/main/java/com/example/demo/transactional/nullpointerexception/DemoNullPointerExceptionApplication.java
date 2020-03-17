package com.example.demo.transactional.nullpointerexception;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import sample.mybatis.dao.StudentDao;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = { "sample.mybatis" })
public class DemoNullPointerExceptionApplication {

	@Autowired
	private StudentDao studentDao;

	public static void main(String[] args) {
		// System.setProperty("debug", "true");
		SpringApplication.run(DemoNullPointerExceptionApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.err.println(studentDao.getClass());
		studentDao.selectStudentById(1);

		try {
			studentDao.finalSelectStudentById(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.in.read();// 防止抛出异常，导致服务停止，无法通过jps获取进程Pid
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Bean
	@Primary
	DataSource h2DataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema-dev.sql")
				.build();
	}

}
