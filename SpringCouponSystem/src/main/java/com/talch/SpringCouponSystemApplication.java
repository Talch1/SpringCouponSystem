package com.talch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class SpringCouponSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctxAnnotationConfigApplicationContext = SpringApplication.run(SpringCouponSystemApplication.class, args);
		 System.out.println("GO!");
	}

}
