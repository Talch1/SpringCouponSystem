package com.talch.beans;

import java.sql.Date;

import javax.persistence.GeneratedValue;
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

	private String description;

	private double amount;

	private Role role;

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue
	public long getId() {
		return Id;
	}

	@Column
	public String getNameString() {
		return name;
	}

	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	@Column()
	public String getDescription() {
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
