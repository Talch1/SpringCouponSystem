package com.talch.beans;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCoupon {
	@OneToMany
	private Company company;
	@ManyToOne
	private Coupon coupon;
}
