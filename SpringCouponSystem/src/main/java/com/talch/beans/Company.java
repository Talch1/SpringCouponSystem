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

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	private long id;

	private String compName;

	private String password;

	private String email;

	private ArrayList<Coupon> cupons;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@Column
	public String getCompName() {
		return compName;
	}

	@Column
	public String getPassword() {
		return password;
	}

	@Column
	public String getEmail() {
		return email;
	}

	@OneToMany
	public ArrayList<Coupon> getCupons() {
		return cupons;
	}

}