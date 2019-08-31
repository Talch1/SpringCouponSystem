package com.talch.beans;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

	@Id
	@GeneratedValue
	private long id;
	@Column
	private String title;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private int amount;
	@Column
	private CouponType type;
	@Column
	private String message;
	@Column
	private double price;
	@Column
	private String image;

}
