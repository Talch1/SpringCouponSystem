package com.talch.beans;



import javax.persistence.GeneratedValue;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {

	private long Id;

	private String name;

	private Date date;

	private Description description;

	private double amount;

	private Role role;

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue
	public long getId() {
		return Id;
	}

	@Column
	public String getName() {
		return name;
	}

	@Column
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	@Column()
	public Description getDescription() {
		return description;
	}

	@Column()
	public double getAmount() {
		return amount;
	}

	@Column()
	public Role getRole() {
		return role;
	}
}
