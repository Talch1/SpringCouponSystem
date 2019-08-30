package com.talch.beans;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
		private long id;
		private String custName;
		private String password;
		private ArrayList<Coupon> coupons;
}