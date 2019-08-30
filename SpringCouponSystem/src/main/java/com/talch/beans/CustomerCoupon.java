package com.talch.beans;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCoupon {
	
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Coupon coupon;

}
