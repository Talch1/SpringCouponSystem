package com.talch.rest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.exeption.ExistEx;
import com.talch.service.UserService;

@RestController
@RequestMapping("company")
public class CompanyController {

	@Autowired
	UserService userService;

	// http://localhost:8080/company/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public List<Coupon> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId) {
		userService.addCouponToUser(userId, couponId);
		List<Coupon> coupons = (List<Coupon>) userService.getAllcouponsByUserId(userId);
		return coupons;

	}

	// http://localhost:8080/company/createCoup
	@PostMapping(value = "/createCoup")
	public List<Coupon> insertCoup(@RequestBody Coupon coup) throws ExistEx {
		if (userService.findCoupById(coup.getId()).isPresent()) {
			throw new ExistEx("This id is exist");
		}
		userService.addCoupon(coup);
		return userService.findAllCoup();

	}

	// http://localhost:8080/company/getCoupByID/{id}
	@GetMapping(value = "/getCoupByID/{id}")
	Optional<Coupon> findById2(@PathVariable Long id) {
		return userService.findCoupById(id);
	}

	// http://localhost:8080/company/getCoupons
	@GetMapping(value = "/getCoupons")
	public List<Coupon> getAllCoupons() {
		return userService.findAllCoup();
	}

	// http://localhost:8080/company/deleteCoup/{id}
	@DeleteMapping(value = "/deleteCoup/{id}")
	public List<Coupon> deleteCoup(@PathVariable Long id) {
		return userService.deleteCoupon(id);
	}

	// http://localhost:8080/company/deleteCoup/All
	@DeleteMapping(value = "/deleteCoup/All")
	public List<Coupon> deleteCoupons() {
		userService.deleteCoupons();
		return userService.findAllCoup();
	}

	// http://localhost:8080/company/coupUpdate
	@PutMapping(value = "/coupUpdate")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		userService.updateCoupon(coupon);
		return coupon;

	}

	// http://localhost:8080/company/getAllCoupByType
	@GetMapping(value = "/getAllCoupByType/{type}")

	public List<Coupon> getAllCouponsByType(@PathVariable CouponType type) {
		return userService.getCouponByType(type);

	}

	// http://localhost:8080/company/getAllCoupByDate
	@GetMapping(value = "/getAllCoupByDate/{date}")
	public List<Coupon> getAllCouponsByDate(@PathVariable Date date) {
		return userService.getCouponByDate(date);

	}

	// http://localhost:8080/company/getAllCoupByPrice
	@GetMapping(value = "/getAllCoupByPrice/{price1}")
	public List<Coupon> getCouponWhenPriceBetwenPrice(@PathVariable Double price1) {
		return userService.getCouponWhenPriceBetwenPrice(price1);
	}
}
