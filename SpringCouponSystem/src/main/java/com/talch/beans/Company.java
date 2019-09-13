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

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Company {

	private long id;

	private String compName;

	private String password;

	private String email;

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

}