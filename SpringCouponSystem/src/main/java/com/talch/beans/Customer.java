package com.talch.beans;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@Autowired
	private User user;

	@Autowired
	private Collection<Coupon> cupons;

	@OneToMany
	public Collection<Coupon> getCupons() {
		return cupons;
	}

	public void setCustName(String name) {
		getUser().setName(name);

	}
	@Id
	public long getId() {
		return id;
	}

	public void setPassword(String name) {
		getUser().setName(name);

	}

	@OneToOne
	public User getUser() {
		return user;
	}

}