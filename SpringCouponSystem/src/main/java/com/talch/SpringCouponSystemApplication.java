package com.talch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringCouponSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponSystemApplication.class, args);

		 System.out.println("GO!");
		 

	}

}
