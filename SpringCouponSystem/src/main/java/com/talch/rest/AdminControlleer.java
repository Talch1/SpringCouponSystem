package com.talch.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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
@RequestMapping("/admin/")
public class AdminControlleer {

	@Autowired
	AdminFacade adminService;
	@Autowired
	CompanyFacade companyService;
	@Autowired
	CouponSystem system;

	
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
	public Optional<User> insertCust(@RequestBody User customer) throws ExistEx {
		customer.setRole(Role.Customer);
		customer.setEmail(null);
		return adminService.insertUser(customer);
	}

	// http://localhost:8080/admin/deleteCust/{custId}/}
	@DeleteMapping(value = "/deleteCust/{custId}")
	public List<User> deleteCustomer(@PathVariable long custId) throws ExistEx {
		if (adminService.getUserById(custId).get().getRole().equals(Role.Customer)) {
			return adminService.deleteUserById(custId, Role.Customer);
		}
		return null;
	}

	// http://localhost:8080/custUpdate/
	@PutMapping(value = "/custUpdate/{id}")
	public User updateCustomer1(@PathVariable long id, @RequestBody User customer) throws ExistEx {

		if (adminService.updateUser(id, customer).getRole().equals(Role.Customer)) {
			return adminService.updateUser(id, customer);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCustByID/{custId}
	@GetMapping(value = "/getCustByID/{custId}")
	Optional<User> findById(@PathVariable long custId) throws ExistEx {
		if (adminService.getUserById(custId).get().getRole().equals(Role.Customer)) {
			return adminService.getUserById(custId);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCustomers
	@GetMapping(value = "/getCustomers")
	public List<User> getAllCustomers() {
		List<User> users = adminService.findAllUsers();
		List<User> customersList = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().equals(Role.Customer)) {
			customersList.add(user);
			}
		}
		return customersList;
	}

	// http://localhost:8080/admin/deleteCust/All
	@DeleteMapping(value = "/deleteCust/All")
	public String deleteCustomers(@RequestBody Role role) {

		return adminService.deleteAllUsers(role);
	}

	// ***********************Company***************************************

	// http://localhost:8080/admin/compnyCreate
	@PostMapping(value = "/compnyCreate")
	public Optional<User> insertCompany(@RequestBody User company) throws ExistEx {
		company.setRole(Role.Company);
		return adminService.insertUser(company);
	}

	// http://localhost:8080/admin/deleteComp/{compId}
	@DeleteMapping(value = "/deleteComp/{compId}")
	public List<User> deleteCompany(@PathVariable long compId) throws ExistEx {
		if (adminService.getUserById(compId).get().getRole().equals(Role.Company)) {
			return adminService.deleteUserById(compId, Role.Company);
		}
		return null;
	}

	// http://localhost:8080/admin/companyUpdate
	@PutMapping(value = "/companyUpdate/{id}")
	public User updateCompany1(@PathVariable long id, @RequestBody User company) throws ExistEx {
		if (adminService.updateUser(id, company).equals(Role.Company)) {
			return adminService.updateUser(id, company);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCompByID/{compId}
	@GetMapping(value = "/getCompByID/{compId}")
	Optional<User> findById1(@PathVariable long compId) throws ExistEx {
		if (adminService.getUserById(compId).get().getRole().equals(Role.Company)) {
			return adminService.getUserById(compId);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCompanys
	@GetMapping(value = "/getCompanys")
	public List<User> getAllCompanys() {

		List<User> users = adminService.findAllUsers();

		List<User> companysList = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().equals(Role.Company)) {
			companysList.add(user);
			}
		}
		return companysList;
	}

	// http://localhost:8080/admin/deleteComp/All
	@DeleteMapping(value = "/deleteComp/All")
	public String deleteCompanys(Role role) {
		adminService.deleteAllUsers(role);
		return "All Companyes deleted";
	}

	// http://localhost:8080/admin/getComByNameAndPass
	@GetMapping(value = "/getComByNameAndPass")
	public User getCompanyByNameAndPass(@RequestParam String name, @RequestParam String pass) {

		return adminService.getUserByNameAndPass(name, pass);
	}

	// http://localhost:8080/admin/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public List<Coupon> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId) throws Exception {
		companyService.addCouponToUser(userId, couponId);
		List<Coupon> coupons = (List<Coupon>) companyService.getAllcouponsByUserId(userId);
		return coupons;

	}

	// http://localhost:8080/admin/getCompCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public Collection<Coupon> getCompCoupons(@PathVariable long id) throws ExistEx {
		Optional<User> user = findById(id);
		if (user.get().getRole().equals(Role.Company)) {
			return companyService.getAllcouponsByUserId(id);
		} else {
			throw new ExistEx("Id is not exist");
		}

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
