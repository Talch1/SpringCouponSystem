package com.talch.rest;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.CouponSystem;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.facade.CompanyFacade;
import com.talch.facade.CustomerFacade;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerFacade customerService;

	@Autowired
	CouponSystem system;

	private CustomSession isActive(String token) {
		return system.getTokensMap().get(token);
	}

	// http://localhost:8080/admin/logout
	@PostMapping(value = "/logout")
	private void logout(@RequestBody String token) {
		system.getTokensMap().remove(token);
	}

	// http://localhost:8080/customer/seeAllCoupons
	@GetMapping(value = "/seeAllCoupons")
	public Collection<Coupon> seeAllCoup() {
		return customerService.getAllCouponsOfAllCompanys();
	}

	// http://localhost:8080/customer/addCouponToCust/{token}
	@PutMapping(value = "/addCouponToCust/{token}")
	public ResponseEntity<?> addCoupon(@PathVariable String token, @RequestBody long coupId) throws ExistEx {
		CustomSession customSession = isActive(token);

		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			((CustomerFacade) customSession.getFacade()).addCouponToUser(userId, coupId);
			List<Coupon> coupons = (List<Coupon>) customerService.getAllcouponsByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getUserById(userId).get().getCupons());
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/customer/getCustCoup/{token}
	@GetMapping(value = "/getCustCoup/{token}")
	public ResponseEntity<?> getCustCoupons(@PathVariable String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			Optional<User> user = customerService.getUserById(userId);
			if (user.get().getRole().equals(Role.Customer)) {
				return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllcouponsByUserId(userId));
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/customer/getCustCoupByID/{token}/{coupId}
	@GetMapping(value = "/getCustCoupByID/{token}/{coupId}")
	public ResponseEntity<?> findCustCoupById(@PathVariable String token, @PathVariable long coupId) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCouponByUserId(userId, coupId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/customer/findCustCoupByType/{token}/{type}
	@GetMapping(value = "/findCustCoupByType/{token}/{type}")
	public ResponseEntity<?> findCustCoupByType(@PathVariable String token, @PathVariable CouponType type)
			throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getUserCouponByType(userId, type));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/customer/findCustCoupByDate/{token}/{date}
	@GetMapping(value = "/findCustCoupByDate/{token}/{date}")
	public ResponseEntity<?> findCustCoupByDate(@PathVariable String token, @PathVariable Date date) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getUserCouponByDateBefore(userId, date));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/customer/findCustCoupByPrice/{token}/{price}
	@GetMapping(value = "/findCustCoupByPrice/{token}/{price}")
	public ResponseEntity<?> findCustCoupByPrice(@PathVariable String token, @PathVariable double price)
			throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CustomerFacade) customSession.getFacade()).getCustId();
			return ResponseEntity.status(HttpStatus.OK)
					.body(customerService.getUserCouponByPriceLessThat(userId, price));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}