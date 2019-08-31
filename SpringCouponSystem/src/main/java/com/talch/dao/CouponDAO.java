package com.talch.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.repo.CouponRepository;

@Repository
public class CouponDAO {
	@Autowired

	private CouponRepository couponRepository;

	public void createCoupon(Coupon coupon) {
		couponRepository.save(coupon);
	}

	public void removeCoupon(Coupon coupon) {
		couponRepository.delete(coupon);
	}

	public void updateCoupon(Coupon coupon, float price, Date endDate) {
		couponRepository.findById(coupon.getId());
		coupon.setPrice(price);
		coupon.setEndDate(endDate);
		couponRepository.save(coupon);
	}

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public Optional<Coupon> getCoupon(long id) {

		return couponRepository.findById(id);
	}

	public Iterable<Coupon> getCouponByType(CouponType type) {
		return couponRepository.findByOne(type);
	}

}
