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
import com.talch.beans.Customer;
import com.talch.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	// http://localhost:8080/customer/addCouponToCust
	@PutMapping(value = "/addCouponToCust/{custId}")
	public List<Coupon> purchouse(@PathVariable long custId, @RequestBody long coupId) {
		customerService.purchouseCoupon(custId, coupId);
		return customerService.findAllCoup(custId);
	}
	// http://localhost:8080/company/getAllCoupons
	@GetMapping(value = "/getAllCoupons/{custId}")
	public List<Coupon> getAllCoupons(@PathVariable long custId) {
		return customerService.findAllCoup(custId);
	}
	// http://localhost:8080/customer/getAllCouponByType
	@GetMapping(value = "/getAllCouponByType/{custId}/{type}")
	public List<Coupon> getAllCouponsByType(@PathVariable long custId, @PathVariable CouponType type) {
		return customerService.getCouponByType(type, custId);

	}
	@GetMapping(value = "/getAllCouponById/{custId}/{coupId}")
	public Coupon getAllCouponById(@PathVariable long custId, @PathVariable long id) {
		return customerService.getCouponById( custId,id);

	}
	// http://localhost:8080/customer/getAllCouponByDate
	@GetMapping(value = "/getAllCouponByDate/{custId}/{date}")
	public List<Coupon> getAllCouponsByDate(@PathVariable long custId, @PathVariable Date date) {
		return customerService.getCouponByDate(date, custId);

	}

	// http://localhost:8080/customer/getAllCouponByPrice
	@GetMapping(value = "/getAllCouponByPrice/{custId}/{price1}")
	public List<Coupon> getCouponWhenPriceBetwenPrice(@PathVariable long custId, @PathVariable Double price1) {
		return customerService.getCouponWhenPriceBetwenPrice(price1, custId);
	}

}