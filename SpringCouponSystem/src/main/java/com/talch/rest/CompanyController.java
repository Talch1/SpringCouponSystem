package com.talch.rest;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

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
import com.talch.service.CompanyService;

@RestController
@RequestMapping("company")
public class CompanyController {

	@Autowired
	CompanyService userService;

	// http://localhost:8080/company/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public List<Coupon> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId) throws ExistEx {
		userService.addCouponToUser(userId, couponId);
		List<Coupon> coupons = (List<Coupon>) userService.getAllcouponsByUserId(userId);
		return coupons;

	}

	// http://localhost:8080/company/createCoup
	@PostMapping(value = "/createCoup/{userId}")
	public List<Coupon> insertCoup(@RequestBody Coupon coup,@PathVariable long userId) throws ExistEx {
		if (userService.findCoupById(coup.getId()).isPresent()) {
			throw new ExistEx("This id is exist");
		}
		userService.addCoupon(coup);
		userService.addCouponToUser(userId, coup.getId());
		return userService.findAllCouponsByUser(userId);

	}

	// http://localhost:8080/company/getCoupByID/{userId}/{coupId}
	@GetMapping(value = "/getCoupByID/{userId}/{coupId}")
	Coupon findById2(@PathVariable long userId,@PathVariable long coupId) {
		return userService.findAllCoupByUser(userId, coupId);
	}

	// http://localhost:8080/company/getCoupons
	@GetMapping(value = "/getCoupons/{userId}")
	public List<Coupon> getAllCoupons(@PathVariable long userId) {
		return userService.findAllCouponsByUser(userId);
	}

	// http://localhost:8080/company/deleteCouponById/{userId}/{coupId}
	@DeleteMapping (value = "/deleteCouponById/{userId}/{coupId}")
	public Collection<Coupon> deleteCouponById(@PathVariable long userId,@PathVariable long coupId){
		return userService.deleteCouponByUser(userId, coupId);
	}
	
	
	
	// http://localhost:8080/company/deleteCoup/All/{userId}
	@DeleteMapping(value = "/deleteCoup/All/{userId}")
	public List<Coupon> deleteCoupons(@PathVariable  long userId) {
		return userService.deleteCouponsByUser(userId);
		
	}

	// http://localhost:8080/company/coupUpdate
	@PutMapping(value = "/coupUpdate/{userId}")
	public Coupon updateCoupon(@RequestBody Coupon coupon,@PathVariable long userId) throws ExistEx {
		userService.updateCoupon(coupon, userId);
		return coupon;

	}

	// http://localhost:8080/company/findUserCoupByType/{userId}/{type}
		@GetMapping(value = "/findUserCoupByType/{userId}/{type}")
		public List<Coupon> findUserCoupByType(@PathVariable long userId, @PathVariable CouponType type) throws ExistEx {
			return userService.getUserCouponByType(userId, type);
		}

		// http://localhost:8080/company/findUserCoupByDate/{userId}/{date}
		@GetMapping(value = "/findUserCoupByDate/{userId}/{date}")
		public List<Coupon> findUserCoupByDate(@PathVariable long userId, @PathVariable Date date) throws ExistEx {
			return userService.getUserCouponByDateBefore(userId, date);
		}
		// http://localhost:8080/company/findUserCoupByPrice/{userId}/{price}
			@GetMapping(value = "/findUserCoupByPrice/{userId}/{price}")
			public List<Coupon> findUserCoupByPrice(@PathVariable long userId, @PathVariable double price) throws ExistEx {
				return userService.getUserCouponByPriceLessThat(userId, price);
			}

}
