package com.talch.rest;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.facade.CustomerFacade;

   @RestController
   @RequestMapping("/customer")
   public class CustomerController {
	@Autowired
	CustomerFacade customerService;


	// http://localhost:8080/customer/addCouponToCust
	@PutMapping(value = "/addCouponToCust/{custId}")
	public Collection<Coupon> addCoupon(@PathVariable long custId, @RequestBody long coupId) throws ExistEx {
		customerService.addCouponToUser(custId, coupId);
		return customerService.getUserById(custId).get().getCupons();
	}

	// http://localhost:8080/customer/getCustCoup
	@GetMapping(value = "/getCustCoup/{id}")
	public Collection<Coupon> getCustCoupons(@PathVariable long id) throws ExistEx {
		Optional<User> user = customerService.getUserById(id);
		if (user.get().getRole().equals(Role.Customer)) {
			return customerService.getAllcouponsByUserId(id);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/customer/getCustCoupByID/{custId}/{coupId}
	@GetMapping(value = "/getCustCoupByID/{custId}/{coupId}")
	public Coupon findCustCoupById(@PathVariable long custId, @PathVariable long coupId) throws ExistEx {
		return customerService.getCouponByUserId(custId, coupId);
	}

	// http://localhost:8080/customer/findCustCoupByType/{userId}/{type}
	@GetMapping(value = "/findCustCoupByType/{userId}/{type}")
	public List<Coupon> findCustCoupByType(@PathVariable long userId, @PathVariable CouponType type) throws ExistEx {
		return customerService.getUserCouponByType(userId, type);
	}

	// http://localhost:8080/customer/findCustCoupByDate/{userId}/{date}
	@GetMapping(value = "/findCustCoupByDate/{userId}/{date}")
	public List<Coupon> findCustCoupByDate(@PathVariable long userId, @PathVariable Date date) throws ExistEx {
		return customerService.getUserCouponByDateBefore(userId, date);
	}

	// http://localhost:8080/customer/findCustCoupByPrice/{userId}/{price}
	@GetMapping(value = "/findCustCoupByPrice/{userId}/{price}")
	public List<Coupon> findCustCoupByPrice(@PathVariable long userId, @PathVariable double price) throws ExistEx {
		return customerService.getUserCouponByPriceLessThat(userId, price);
	}

}