package com.talch.beans;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class User {

	private long id;

	private Role role;

	private String userName;

	private String password;

	private String email;

	private double amount;
	
	@Autowired
	private Collection<Coupon> cupons;

	@Id
	@Column(unique = true, nullable = false)
	public long getId() {
		return id;
	}

	@ManyToMany
	public Collection<Coupon> getCupons() {
		return cupons;
	}

	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}

	@Column
	public String getUserName() {
		return userName;
	}

	@Column()
	public String getPassword() {
		return password;
	}

	@Column()
	public String getEmail() {
		return email;
	}
	@Column
	public double getAmount() {
		return amount;
	}

	

}
