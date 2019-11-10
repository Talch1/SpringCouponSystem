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
public class CompanyService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;

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
	public Optional<Coupon> findCoupById(long id) {
		return couponRepository.findById(id);
	}
	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}
	public List<Coupon> findAllCouponsByUser(long userId) {
		return (List<Coupon>) userRepository.getOne(userId).getCupons();
	}
	public Collection<Coupon> deleteCouponByUser(long userId, long coupId) {

		User user = userRepository.getOne(userId);
		List<Coupon> list = (List<Coupon>) user.getCupons();
		List<Coupon> list2 = new ArrayList<Coupon>();
		for (Coupon coupon : list) {
			if (coupon.getId() != coupId) {
				list2.add(coupon);
			}
		}
		user.setCupons(list2);
		userRepository.save(user);
		return list2;
	}
	public Collection<Coupon> getAllcouponsByCompId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}

	public Collection<Coupon> getAllcouponsByUserId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}
	public List<Coupon> deleteCouponsByUser(long userId) {
		Optional<User> user = userRepository.findById(userId);
		user.get().setCupons(null);
		return (List<Coupon>) user.get().getCupons();
	}
	public Coupon updateCoupon(Coupon coupon, long userId) throws ExistEx {
		boolean check = false;
		List<Coupon> list = (List<Coupon>) userRepository.getOne(userId).getCupons();
		for (Coupon coupon2 : list) {
			if (coupon.getId() == coupon2.getId()) {
				check = true;
			}
		}
		if (check) {

			Coupon coupToUpdate = couponRepository.getOne(coupon.getId());
			coupToUpdate.setEndDate(coupon.getEndDate());

			coupToUpdate.setPrice(coupon.getPrice());
			couponRepository.save(coupToUpdate);
		} else {
			throw new ExistEx("You don't have this coupon");
		}
		return couponRepository.getOne(coupon.getId());
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
	public Coupon findAllCoupByUser(long userId, long coupId) {
		List<Coupon> coupons = (List<Coupon>) userRepository.getOne(userId).getCupons();
		Coupon coupon2 = new Coupon();
		for (Coupon coupon : coupons) {
			if (coupId == coupon.getId()) {
				coupon2 = couponRepository.getOne(coupId);
			}
		}
		return coupon2;
	}

}
