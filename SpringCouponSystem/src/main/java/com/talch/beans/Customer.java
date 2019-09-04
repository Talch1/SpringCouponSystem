package com.talch.beans;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;


@Table
@Entity
public class Customer {

	private long id;

	private String custName;

	private String password;
	@Autowired
	private ArrayList<Coupon> coupons;

	
	
	public Customer(long id, String custName, String password, ArrayList<Coupon> coupons) {
		setId(id);
	    setCustName(custName);
		setPassword(password);
		setCoupons(coupons);
	}
	

	public Customer() {
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getCustName() {
		return custName;
	}

	@Column
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}
	
	

}