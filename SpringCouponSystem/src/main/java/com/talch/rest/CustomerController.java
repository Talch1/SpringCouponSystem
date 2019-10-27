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
import com.talch.service.UserService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	UserService userService;

	// http://localhost:8080/customer/addCouponToCust
		@PutMapping(value = "/addCouponToCust/{custId}")
		public Collection<Coupon> addCoupon(@PathVariable long custId, @RequestBody long coupId) {
			userService.addCouponToUser(custId, coupId);
			return userService.getUserById(custId).get().getCupons();
		}
		
	/// http://localhost:8080/customer/getCustCouponsById
		@GetMapping(value = "/getCustCoup/{id}")
		public Collection<Coupon> getCustCoupons(@PathVariable long id) throws ExistEx {
			Optional<User> user = userService.getUserById(id);
			if (user.get().getRole().equals(Role.Customer)) {
				return userService.getAllcouponsByUserId(id);
			} else {
				throw new ExistEx("Id is not exist");
			}
		}
	// http://localhost:8080/customer/findCustCoupByType/{CustId}
	@GetMapping(value = "/findCustCoupByType/{custId}")
	public List<Coupon> findCustCoupByType(@PathVariable long custId, @RequestBody CouponType type) throws ExistEx {
		return userService.getCustCouponByType(custId, type);
	}

	// http://localhost:8080/customer/findCustCoupByDate/{custId}
	@GetMapping(value = "/findCustCoupByDate/{custId}")
	public List<Coupon> findCustCoupByDate(@PathVariable long custId, @RequestBody Date date) throws ExistEx {
		return userService.getCustCouponByDateBefore(custId, date);
	}
	// http://localhost:8080/customer/findCustCoupByPrice/{custId}
		@GetMapping(value = "/findCustCoupByPrice/{custId}")
		public List<Coupon> findCustCoupByPrice(@PathVariable long custId, @RequestBody double price) throws ExistEx {
			return userService.getCustCouponByPriceLessThat(custId, price);
		}

}