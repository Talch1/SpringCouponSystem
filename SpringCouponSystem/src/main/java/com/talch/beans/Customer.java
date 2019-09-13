package com.talch.beans;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Customer {

	private long id;

	private String custName;

	private String password;

	@Autowired
	private Collection<Coupon> cupons;

	@Id
	public long getId() {
		return id;
	}

	@OneToMany
	public Collection<Coupon> getCupons() {
		return cupons;
	}

	@Column(unique = true)
	public String getCustName() {
		return custName;
	}

	@Column
	public String getPassword() {
		return password;
	}

}