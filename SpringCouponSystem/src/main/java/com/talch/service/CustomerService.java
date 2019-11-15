package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;
@Service
@Transactional
public class CustomerService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;
	
	
	public void addCoupons(long userId, List<Coupon> coupons) {
		User userToUpdate = userRepository.getOne(userId);
		userToUpdate.setCupons(coupons);
		userRepository.save(userToUpdate);

	}
	public void addCouponToUser(long userId, long coupId) throws ExistEx {
		Coupon coupon = couponRepository.getOne(coupId);
		User userToUpdate = userRepository.getOne(userId);
		List<Coupon> coupons = (List<Coupon>) userToUpdate.getCupons();
		if (coupons.contains(coupon)) {
			throw new ExistEx("You have this coupon");
		}
		coupons.add(coupon);
		userRepository.save(userToUpdate);

	}
	
	public Collection<Coupon> getAllcouponsByUserId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	

	public Coupon getCouponByUserId(long custId, long coupId) throws ExistEx {
		Optional<User> user = userRepository.findById(custId);
		Coupon userCoupon = new Coupon();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId() == coupId) {
				userCoupon = coupon;
			} else {
				throw new ExistEx("This coupon not exist");
			}
		}
		return userCoupon;

	}
	public List<Coupon> getUserCouponByPriceLessThat(long userId, double price) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> userCoupons = new ArrayList<Coupon>();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < price) {
				userCoupons.add(coupon);
			}
		}
		return userCoupons;
	}

	public List<Coupon> getUserCouponByType(long userId, CouponType type) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getType() != type) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getUserCouponByDateBefore(long userId, Date date) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> userCoupons = new ArrayList<Coupon>();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(date)) {
				userCoupons.add(coupon);
			}
		}
		return userCoupons;
	}
}
