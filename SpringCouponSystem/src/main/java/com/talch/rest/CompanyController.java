package com.talch.rest;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.CouponSystem;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
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

	// http://localhost:8080/company/addCouponToComp/{token}
	@PostMapping(value = "/addCouponToComp/{token}")
	public ResponseEntity<?> addCouponsToComp(@PathVariable String token, @RequestBody long coupId) {
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

	// http://localhost:8080/company/createCoup/{token}
	@PostMapping(value = "/createCoup/{token}")
	public ResponseEntity<?> insertCoup(@RequestBody Coupon coup, @PathVariable String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			userService.addCoupon(coup, userId);
			return ResponseEntity.status(HttpStatus.OK).body(userService.findAllCouponsByUser(userId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/getCoupByID/{token}/{coupId}
	@GetMapping(value = "/getCoupByID/{token}/{coupId}")
	public ResponseEntity<?> getCoupByID(@PathVariable String token, @PathVariable long coupId) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.findCoupfromUserbyCouponId(userId, coupId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/deleteCouponById/{token}/{coupId}
	@DeleteMapping(value = "/deleteCouponById/{token}/{coupId}")
	public ResponseEntity<?> deleteCouponById(@PathVariable String token, @PathVariable long coupId) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.deleteCouponByUser(userId, coupId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/getCoupons/{token}"
	@GetMapping(value = "/getCoupons/{token}")
	public ResponseEntity<?> getAllCoupons(@PathVariable String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			return ResponseEntity.status(HttpStatus.OK).body(userService.findAllCouponsByUser(userId));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/deleteCoup/All/{token}
	@DeleteMapping(value = "/deleteCoup/All/{token}")
	public ResponseEntity<?> deleteCoupons(@PathVariable String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.OK)
					.body(userService.deleteCouponsByUser(((CompanyFacade) customSession.getFacade()).getCompId()));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// http://localhost:8080/company/coupUpdate/{token}
	@PutMapping(value = "/coupUpdate/{token}")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @PathVariable String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			userService.updateCoupon(coupon, userId);
				return ResponseEntity.status(HttpStatus.OK).body(userService.findCoupById(userId));
			}		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	// http://localhost:8080/company/findUserCoupByType/{token}/{type}
	@GetMapping(value = "/findUserCoupByType/{token}/{type}")
	public ResponseEntity<?> findUserCoupByType(@PathVariable String token, @PathVariable String type) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
			
				return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByType(userId, type));
			}		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	// http://localhost:8080/company/findUserCoupByDate/{token}/{date}
		@GetMapping(value = "/findUserCoupByDate/{token}/{date}")
	public ResponseEntity<?> findUserCoupByDate(@PathVariable String token, @PathVariable Date date) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
				return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByDateBefore(userId, date));
			}		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

		// http://localhost:8080/company/findUserCoupByPrice/{token}/{price}
		@GetMapping(value = "/findUserCoupByPrice/{token}/{price}")
			public ResponseEntity<?> findUserCoupByPrice(@PathVariable String token, @PathVariable double price) {
				CustomSession customSession = isActive(token);
				if (customSession != null) {
					long userId = ((CompanyFacade) customSession.getFacade()).getCompId();
						return ResponseEntity.status(HttpStatus.OK).body(userService.getUserCouponByPriceLessThat(userId, price));
					}		return new ResponseEntity(HttpStatus.NOT_FOUND);
			}

}
