package com.talch.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.CouponSystem;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/")
public class AdminControlleer {

	private final AdminFacade adminService;

	private final CompanyFacade companyService;

	private final Utils utils;


	// http://localhost:8081/v1/admin//logout
	@PostMapping(value = "/logout")
	private void logout(@RequestHeader String token) {
		utils.getSystem().getTokensMap().remove(token);
	}


//******************************Customer**********************************

	// http://localhost:8081/v1/admin//customerCreate
	@PostMapping(value = "/customerCreate")
	public ResponseEntity<?> customerCreate(@RequestBody User customer, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);

		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			customer.setRole(Role.Customer);
			customer.setAmount(1000);
			customer.setEmail(null);
			return adminService.insertUser(customer);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/deleteCust/{custId}}
	@DeleteMapping(value = "/deleteCust/{custId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable long custId, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.deleteUserById(custId, Role.Customer);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/custUpdate/{id}
	@PutMapping(value = "/custUpdate/{id}")
	public ResponseEntity<?> updateCustomer1(@PathVariable long id, @RequestBody User customer,
											 @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.updateUser(id, customer, Role.Customer);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/getCustByID/{custId}
	@GetMapping(value = "/getCustByID/{custId}")
	public ResponseEntity<?> findById(@PathVariable long custId, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.getUserById(custId,Role.Customer);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/getCustomers
	@GetMapping(value = "/getCustomers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.findAllCust();
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/deleteCust/All
	@DeleteMapping(value = "/deleteCust/All")
	public ResponseEntity<String> deleteCustomers(@RequestBody Role role, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.deleteAllUsers(role);
		}
		return utils.getResponseEntitySesionNull();
	}

	// ***********************Company***************************************

	// http://localhost:8081/v1/admin/companyCreate
	@PostMapping(value = "/companyCreate")
	public ResponseEntity<?> companyCreate(@RequestBody User company, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token));
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			company.setRole(Role.Company);
			company.setAmount(100000);
			return adminService.insertUser(company);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/deleteComp/{compId}
	@DeleteMapping(value = "/deleteComp/{compId}")
	public ResponseEntity<?> deleteCompany(@PathVariable long compId, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());

			return adminService.deleteUserById(compId, Role.Company);

		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/companyUpdate
	@PutMapping(value = "/companyUpdate/{id}")
	public ResponseEntity<?> companyUpdate(@PathVariable long id, @RequestBody User company,
											@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.updateUser(id, company, Role.Company);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/getCompByID/{compId}
	@GetMapping(value = "/getCompByID/{compId}")
	public ResponseEntity<?> getCompByID(@PathVariable long compId, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
				return adminService.getUserById(compId,Role.Company);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/getCompanys
	@GetMapping(value = "/getCompanys")
	public ResponseEntity<?> companys(@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.findAllComp();
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/deleteComp/All
	@DeleteMapping(value = "/deleteComp/All")
	public ResponseEntity<String> deleteCompanys(Role role, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return adminService.deleteAllUsers(role);
		}
		return utils.getResponseEntitySesionNull();

	}

	// http://localhost:8081/v1/admin/getComByNameAndPass
	@GetMapping(value = "/getComByNameAndPass")
	public ResponseEntity<?> getCompanyByNameAndPass(@RequestParam String name, @RequestParam String pass,
			@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());

			return adminService.getUserByNameAndPass(name, pass,Role.Company);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/admin/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public ResponseEntity<?> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId,
			@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return companyService.addCouponToUser(userId, couponId);
		}
		return utils.getResponseEntitySesionNull();
	}

	// http://localhost:8081/v1/admin/getCompCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public ResponseEntity<?> getCompCoupons(@PathVariable long id, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
				return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllcouponsByUserId(id,Role.Company));

		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// ***********************Coupon*****************************************

	// http://localhost:8080/admin/createCoup
	@PostMapping(value = "/createCoup")
	public ResponseEntity<?> insertCoup(@RequestBody Coupon coup, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			if (adminService.findCoupById(coup.getId()).isPresent()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			adminService.addCoupon(coup);
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllCoup());
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCoupByID/{id}
	@GetMapping(value = "/getCoupByID/{id}")
	public ResponseEntity<?> findById2(@PathVariable Long id, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findCoupById(id));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCoupons
	@GetMapping(value = "/getCoupons")
	public ResponseEntity<?> getAllCoupons(@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {

			return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllCoup());
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteCoup/{id}
	@DeleteMapping(value = "/deleteCoup/{id}")
	public ResponseEntity<?> deleteCoup(@PathVariable Long id, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {

			return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteCoupon(id));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteCoup/All
	@DeleteMapping(value = "/deleteCoup/All")
	public ResponseEntity<?> deleteCoupons(@RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			adminService.deleteCoupons();
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllCoup());
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/coupUpdate
	@PutMapping(value = "/coupUpdate")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {

			adminService.updateCouponAdmin(coupon);

			return ResponseEntity.status(HttpStatus.OK).body(coupon);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getAllCoupByType
	@GetMapping(value = "/getAllCoupByType/{type}")
	public ResponseEntity<?> getAllCouponsByType(@PathVariable CouponType type, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getCouponByType(type));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getAllCoupByDate
	@GetMapping(value = "/getAllCoupByDate/{date}")
	public ResponseEntity<?> getAllCouponsByDate(@PathVariable Date date, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getCouponByDate(date));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getAllCoupByPrice
	@GetMapping(value = "/getAllCoupByPrice/{price1}")
	public ResponseEntity<?> getCouponWhenPriceBetwenPrice(@PathVariable Double price1, @RequestHeader String token) {
		CustomSession customSession = utils.isActive(token);
		if (customSession != null) {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getCouponWhenPriceBetwenPrice(price1));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
