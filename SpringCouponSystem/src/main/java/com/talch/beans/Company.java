package com.talch.beans;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
public class Company {

	private long id;

	private String compName;

	private String password;

	private String email;

	@Autowired
	private ArrayList<Coupon> cupons;

	
	
	public Company(long id, String compName, String password, String email, ArrayList<Coupon> cupons) {
		setId(id);
		setCompName(compName);
		setPassword(password);
	    setEmail(email);
		setCupons(cupons);
	}

	
	public Company() {
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	
	public ArrayList<Coupon> getCupons() {
		return cupons;
	}

	public void setCupons(ArrayList<Coupon> cupons) {
		this.cupons = cupons;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
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


	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", cupons=" + cupons + "]";
	}

}