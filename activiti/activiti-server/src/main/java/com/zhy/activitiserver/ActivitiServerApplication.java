package com.zhy.activitiserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhy.activitiserver.mapper")
public class ActivitiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiServerApplication.class, args);
	}
}
