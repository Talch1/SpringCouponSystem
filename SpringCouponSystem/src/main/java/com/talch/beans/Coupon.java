package com.talch.beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@Column(unique = true)
	public String getTitle() {
		return title;
	}

	@Column(columnDefinition = "Date")
	public Date getStartDate() {
		return startDate;
	}

	@Column(columnDefinition = "Date")
	public Date getEndDate() {
		return endDate;
	}

	@Column(columnDefinition = "int")
	public int getAmount() {
		return amount;
	}

	@Enumerated
	@Column
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
