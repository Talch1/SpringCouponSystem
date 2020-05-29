package com.talch;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class SpringCouponSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCouponSystemApplication.class, args);
		 System.out.println("GO!");
	}

}
