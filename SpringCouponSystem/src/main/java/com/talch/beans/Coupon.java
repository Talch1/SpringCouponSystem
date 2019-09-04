package com.talch.beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
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

	
	
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}

	
	public Coupon() {
		
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	public CouponType getType() {
		return type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
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


	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	

}
