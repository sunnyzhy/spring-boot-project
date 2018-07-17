package com.zhy.activitiserver5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@SpringBootApplication
@EnableTransactionManagement
public class ActivitiServer5Application {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiServer5Application.class, args);
	}
}
