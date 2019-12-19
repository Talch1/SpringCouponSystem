package com.talch.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import antlr.Token;

@RestController
@RequestMapping("/admin/")
public class AdminControlleer {

	@Autowired
	AdminFacade adminService;
	@Autowired
	CompanyFacade companyService;
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

	// http://localhost:8080/admin/getAllIncome
	@GetMapping(value = "/getAllIncome")
	public List<Income> getAllIncome() {
		return (List<Income>) adminService.viewAllIncomes();
	}
//******************************Customer**********************************

	// http://localhost:8080/admin/customerCreate
	@PostMapping(value = "/customerCreate")
	public ResponseEntity<?> insertCust(@RequestBody User customer, @RequestHeader String token) throws ExistEx {
		CustomSession customSession = isActive(token);

		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			customer.setRole(Role.Customer);
			customer.setEmail(null);
			return ResponseEntity.status(HttpStatus.OK).body(adminService.insertUser(customer));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteCust/{custId}}
	@DeleteMapping(value = "/deleteCust/{custId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable long custId, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			if (adminService.getUserById(custId).get().getRole().equals(Role.Customer)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUserById(custId, Role.Customer));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/custUpdate/{id}
	@PutMapping(value = "/custUpdate/{id}")
	public ResponseEntity<?> updateCustomer1(@PathVariable long id, @RequestBody User customer,
			@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			if (adminService.updateUser(id, customer).getRole().equals(Role.Customer)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.updateUser(id, customer));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCustByID/{custId}
	@GetMapping(value = "/getCustByID/{custId}")
	public ResponseEntity<?> findById(@PathVariable long custId, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			if (adminService.getUserById(custId).get().getRole().equals(Role.Customer)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.getUserById(custId));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCustomers
	@GetMapping(value = "/getCustomers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			List<User> users = adminService.findAllUsers();
			List<User> customersList = new ArrayList<User>();
			for (User user : users) {
				if (user.getRole().equals(Role.Customer)) {
					customersList.add(user);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(customersList);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteCust/All
	@DeleteMapping(value = "/deleteCust/All")
	public ResponseEntity<?> deleteCustomers(@RequestBody Role role, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteAllUsers(role));

		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	// ***********************Company***************************************

	// http://localhost:8080/admin/compnyCreate
	@PostMapping(value = "/compnyCreate")
	public ResponseEntity<?> insertCompany(@RequestBody User company, @RequestHeader String token) throws ExistEx {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			company.setRole(Role.Company);
			return ResponseEntity.status(HttpStatus.OK).body(adminService.insertUser(company));
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteComp/{compId}
	@DeleteMapping(value = "/deleteComp/{compId}")
	public ResponseEntity<?> deleteCompany(@PathVariable long compId, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());

			if (adminService.getUserById(compId).get().getRole().equals(Role.Company)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUserById(compId, Role.Company));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/companyUpdate
	@PutMapping(value = "/companyUpdate/{id}")
	public ResponseEntity<?> updateCompany1(@PathVariable long id, @RequestBody User company,
			@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			if (adminService.updateUser(id, company).equals(Role.Company)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.updateUser(id, company));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCompByID/{compId}
	@GetMapping(value = "/getCompByID/{compId}")
	public ResponseEntity<?> findById1(@PathVariable long compId, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			if (adminService.getUserById(compId).get().getRole().equals(Role.Company)) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.getUserById(compId));
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/getCompanys
	@GetMapping(value = "/getCompanys")
	public ResponseEntity<?> getAllCompanys(@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			List<User> users = adminService.findAllUsers();
			List<User> companysList = new ArrayList<User>();
			for (User user : users) {
				if (user.getRole().equals(Role.Company)) {
					companysList.add(user);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(companysList);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/admin/deleteComp/All
	@DeleteMapping(value = "/deleteComp/All")
	public ResponseEntity<?> deleteCompanys(Role role, @RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
			adminService.deleteAllUsers(role);
			return ResponseEntity.status(HttpStatus.OK).body("All Companys was deleted");
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);

	}

	// http://localhost:8080/admin/getComByNameAndPass
	@GetMapping(value = "/getComByNameAndPass")
	public ResponseEntity<?> getCompanyByNameAndPass(@RequestParam String name, @RequestParam String pass,@RequestHeader String token) {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.OK).body(adminService.getUserByNameAndPass(name, pass));
	}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	// http://localhost:8080/admin/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public ResponseEntity<?> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId,@RequestHeader String token)  {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
		companyService.addCouponToUser(userId, couponId);
		List<Coupon> coupons = (List<Coupon>) companyService.getAllcouponsByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(coupons);
	}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	// http://localhost:8080/admin/getCompCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public ResponseEntity<?> getCompCoupons(@PathVariable long id,@RequestHeader String token)  {
		CustomSession customSession = isActive(token);
		if (customSession != null) {
			customSession.setLastAccessed(System.currentTimeMillis());
		Optional<User> user = adminService.getUserById(id);
		if (user.get().getRole().equals(Role.Company)) {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllcouponsByUserId(id));
		}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// ***********************Coupon*****************************************

	// http://localhost:8080/admin/createCoup
	@PostMapping(value = "/createCoup")
	public List<Coupon> insertCoup(@RequestBody Coupon coup) throws ExistEx {
		if (adminService.findCoupById(coup.getId()).isPresent()) {
			throw new ExistEx("This id is exist");
		}
		adminService.addCoupon(coup);
		return adminService.findAllCoup();

	}

	// http://localhost:8080/admin/getCoupByID/{id}
	@GetMapping(value = "/getCoupByID/{id}")
	Optional<Coupon> findById2(@PathVariable Long id) {
		return adminService.findCoupById(id);
	}

	// http://localhost:8080/admin/getCoupons
	@GetMapping(value = "/getCoupons")
	public List<Coupon> getAllCoupons() {
		return adminService.findAllCoup();
	}

	// http://localhost:8080/admin/deleteCoup/{id}
	@DeleteMapping(value = "/deleteCoup/{id}")
	public List<Coupon> deleteCoup(@PathVariable Long id) {
		return adminService.deleteCoupon(id);
	}

	// http://localhost:8080/admin/deleteCoup/All
	@DeleteMapping(value = "/deleteCoup/All")
	public List<Coupon> deleteCoupons() {
		adminService.deleteCoupons();
		return adminService.findAllCoup();
	}

	// http://localhost:8080/admin/coupUpdate
	@PutMapping(value = "/coupUpdate")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		adminService.updateCouponAdmin(coupon);

		return coupon;

	}

	// http://localhost:8080/admin/getAllCoupByType
	@GetMapping(value = "/getAllCoupByType/{type}")

	public List<Coupon> getAllCouponsByType(@PathVariable CouponType type) {
		return adminService.getCouponByType(type);

	}

	// http://localhost:8080/admin/getAllCoupByDate
	@GetMapping(value = "/getAllCoupByDate/{date}")
	public List<Coupon> getAllCouponsByDate(@PathVariable Date date) {
		return adminService.getCouponByDate(date);

	}

	// http://localhost:8080/admin/getAllCoupByPrice
	@GetMapping(value = "/getAllCoupByPrice/{price1}")
	public List<Coupon> getCouponWhenPriceBetwenPrice(@PathVariable Double price1) {
		return adminService.getCouponWhenPriceBetwenPrice(price1);
	}
}
