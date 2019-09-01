package com.talch.beans;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private long id;

	private String custName;

	private String password;

	private ArrayList<Coupon> coupons;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@Column
	public String getCustName() {
		return custName;
	}

	@Column
	public String getPassword() {
		return password;
	}

	@OneToMany
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
}