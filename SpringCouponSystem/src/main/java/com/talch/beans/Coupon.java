package com.talch.beans;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Coupon {

	private long id;
	private String title;
	@Autowired
	private Date startDate;
	@Autowired
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	@Column(unique = true)
	public String getTitle() {
		return title;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	@Column(columnDefinition = "int")
	public int getAmount() {
		return amount;
	}

	@Enumerated(EnumType.STRING)
	public CouponType getType() {
		return type;
	}

	@Column
	public String getMessage() {
		return message;
	}

	@Column(columnDefinition = "double")
	public double getPrice() {
		return price;
	}

	@Column
	public String getImage() {
		return image;
	}

}
