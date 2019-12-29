package com.talch.rest;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.CouponSystem;
import com.talch.beans.Coupon;

import com.talch.exeption.ExistEx;
import com.talch.facade.CompanyFacade;

@RestController
@RequestMapping("company")
public class CompanyController {

	@Autowired
	CompanyFacade userService;

	@Autowired
	CouponSystem system;

	private CustomSession isActive(String token) {
		return system.getTokensMap().get(token);
	}

	// http://localhost:8080/company/logout
	@PostMapping(value = "/logout")
	private void logout(@RequestHeader String token) {
		system.getTokensMap().remove(token);
	}

	// http://localhost:8080/company/seeAllCoupons
	@GetMapping(value = "/seeAllCoupons")
	public Collection<Coupon> seeAllCoup() {
		return userService.getAllCouponsOfAllCompanys();
	}
	// http://localhost:8080/company/addCouponToComp
	@PostMapping(value = "/addCouponToComp")
	public ResponseEntity<?> addCouponsToComp(@RequestHeader String token, @RequestBody long coupId) {
		CustomSession customSession = isActive(token);

		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			((CompanyFacade) customSession.getFacade()).addCouponToUser(userId, coupId);
			List<Coupon> coupons = (List<Coupon>) userService.getAllcouponsByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(coupons);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/createCoup
	@PostMapping(value = "/createCoup")
	public ResponseEntity<?> insertCoup(@RequestBody Coupon coup, @RequestHeader String token) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			userService.addCoupon(coup, userId);
			return ResponseEntity.status(HttpStatus.OK).body(userService.findAllCouponsByUser(userId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/getCoupByID/{coupId}
	@GetMapping(value = "/getCoupByID/{coupId}")
	public ResponseEntity<?> getCoupByID(@RequestHeader String token, @PathVariable long coupId) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.findCoupfromUserbyCouponId(userId, coupId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/deleteCouponById/{coupId}
	@DeleteMapping(value = "/deleteCouponById/{coupId}")
	public ResponseEntity<?> deleteCouponById(@RequestHeader String token, @PathVariable long coupId) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.deleteCouponByUser(userId, coupId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/getCoupons"
	@GetMapping(value = "/getCoupons")
	public ResponseEntity<?> getAllCoupons(@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.findAllCouponsByUser(userId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/deleteCoup/All/
	@DeleteMapping(value = "/deleteCoup/All")
	public ResponseEntity<?> deleteCoupons(@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.OK)
					.body(userService.deleteCouponsByUser(((CompanyFacade) customSession.getFacade()).getCompId()));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/coupUpdate
	@PutMapping(value = "/coupUpdate")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			userService.updateCoupon(coupon, userId);
			return ResponseEntity.status(HttpStatus.OK).body(userService.findCoupById(userId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/findUserCoupByType{type}
	@GetMapping(value = "/findUserCoupByType/{type}")
	public ResponseEntity<?> findUserCoupByType(@RequestHeader String token, @PathVariable String type) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();

			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByType(userId, type));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/findUserCoupByDate/{date}
	@GetMapping(value = "/findUserCoupByDate/{date}")
	public ResponseEntity<?> findUserCoupByDate(@RequestHeader String token, @PathVariable Date date) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByDateBefore(userId, date));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/findUserCoupByPrice/{price}
	@GetMapping(value = "/findUserCoupByPrice/{price}")
	public ResponseEntity<?> findUserCoupByPrice(@RequestHeader String token, @PathVariable double price) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByPriceLessThat(userId, price));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
