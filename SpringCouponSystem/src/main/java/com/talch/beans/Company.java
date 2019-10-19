package com.talch.beans;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Company {

	


	private long id;
	@Autowired
	private User user;

	private String email;

	@Autowired
	private Collection<Coupon> cupons;

	@OneToMany
	public Collection<Coupon> getCupons() {
		return cupons;
	}
@Id
	public long getId() {
		return id;
	}

	@Column
	public String getEmail() {
		return email;
	}

	@OneToOne
	public User getUser() {
		return user;
	}

	public void setName(String name) {
		user.setName(name);

	}

	public void setPassword(String password) {
		user.setPassword(password);

	}



}