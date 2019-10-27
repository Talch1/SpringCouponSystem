package com.talch.rest;

import java.sql.Date;
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

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.service.UserService;



@RestController
@RequestMapping("/admin/")
public class AdminControlleer {

	@Autowired
	UserService adminService;

//******************************Customer**********************************

	// http://localhost:8080/admin/customerCreate
	@PostMapping(value = "/customerCreate")
	public User insertCust(@RequestBody User customer) throws ExistEx {
		if (adminService.getUserById(customer.getId()).isPresent()) {
			throw new ExistEx("This id is exist");
		}
		customer.setRole(Role.Customer);
		adminService.insertUser(customer);
		return customer;
	}

	// http://localhost:8080/admin/getCustByID/{id}
	@GetMapping("/getCustByID/{custId}/{coupId}")
	Optional<User> findById(@PathVariable long id) throws ExistEx {
		if (adminService.getUserById(id).get().getRole().equals(Role.Customer)) {
			return adminService.getUserById(id);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCustomers
	@GetMapping(value = "/getCustomers")
	public List<User> getAllCustomers() {

		List<User> users = adminService.findAllUsers();

		for (User user : users) {
			if (!user.getRole().equals(Role.Customer)) {
				users.remove(user);
			}
		}
		return users;
	}

	// http://localhost:8080/admin/deleteCust/{id}
	@DeleteMapping(value = "/deleteCust/{id}")
	public List<User> deleteCustomer(@PathVariable long id) throws ExistEx {
		Optional<User> user = adminService.getUserById(id);
		if (user.get().getRole().equals(Role.Customer)) {
			return adminService.deleteUserById(id);
		} else {
			throw new ExistEx("Id is not exist");
		}

	}

	// http://localhost:8080/admin/deleteCust/All
	@DeleteMapping(value = "/deleteCust/All")
	public String deleteCustomers(@RequestBody Role role) {

		return adminService.deleteAllUsers(role);
	}

	// http://localhost:8080/custUpdate/
	@PutMapping(value = "/custUpdate/{id}")
	public User updateCustomer1(@PathVariable long id, @RequestBody User customer) {
		adminService.updateUser(id, customer);
		return customer;
	}

	// http://localhost:8080/admin/addCouponToCust
	@PutMapping(value = "/addCouponToCust/{custId}")
	public Collection<Coupon> addCoupon(@PathVariable long custId, @RequestBody long coupId) {
		adminService.addCouponToUser(custId, coupId);
		return adminService.getUserById(custId).get().getCupons();
	}

	// http://localhost:8080/admin/getCustCouponsById
	@GetMapping(value = "/getCustCoup/{id}")
	public Collection<Coupon> getCustCoupons(@PathVariable long id) throws ExistEx {
		Optional<User> user = findById(id);
		if (user.get().getRole().equals(Role.Customer)) {
			return adminService.getAllcouponsByUserId(id);
		} else {
			throw new ExistEx("Id is not exist");
		}

	}

	// http://localhost:8080/admin/getCustCoupByID/{custId}/{coupId}
	@GetMapping(value = "/getCustCoupByID/{custId}/{coupId}")
	public Coupon findCustCoupById(@PathVariable long custId, @PathVariable long coupId) throws ExistEx {
		return adminService.getCouponByCustId(custId, coupId);
	}

	// http://localhost:8080/admin/findCustCoupByType/{CustId}
	@GetMapping(value = "/findCustCoupByType/{custId}")
	public List<Coupon> findCustCoupByType(@PathVariable long custId, @RequestBody CouponType type) throws ExistEx {
		return adminService.getCustCouponByType(custId, type);
	}

	// http://localhost:8080/admin/findCustCoupByDate/{custId}
	@GetMapping(value = "/findCustCoupByDate/{custId}")
	public List<Coupon> findCustCoupByDate(@PathVariable long custId, @RequestBody Date date) throws ExistEx {
		return adminService.getCustCouponByDateBefore(custId, date);
	}
	// http://localhost:8080/admin/findCustCoupByPrice/{custId}
		@GetMapping(value = "/findCustCoupByPrice/{custId}")
		public List<Coupon> findCustCoupByPrice(@PathVariable long custId, @RequestBody double price) throws ExistEx {
			return adminService.getCustCouponByPriceLessThat(custId, price);
		}

	// ***********************Company***************************************

	// http://localhost:8080/admin/compnyCreate
	@PostMapping(value = "/compnyCreate")
	public List<User> insertCompany(@RequestBody User company) throws ExistEx {
		if (adminService.getUserById(company.getId()).isPresent()) {
			throw new ExistEx("This id is exist");
		}
		company.setRole(Role.Company);
		return adminService.insertUser(company);

	}

	// http://localhost:8080/admin/getCompByID/{id}
	@GetMapping(value = "/getCompByID/{id}")
	Optional<User> findById1(@PathVariable long id) throws ExistEx {
		if (adminService.getUserById(id).get().getRole().equals(Role.Company)) {
			return adminService.getUserById(id);
		} else {
			throw new ExistEx("Id is not exist");
		}
	}

	// http://localhost:8080/admin/getCompanys
	@GetMapping(value = "/getCompanys")
	public List<User> getAllCompanys() {

		List<User> users = adminService.findAllUsers();

		for (User user : users) {
			if (!user.getRole().equals(Role.Company)) {
				users.remove(user);
			}
		}
		return users;
	}

	// http://localhost:8080/admin/deleteComp/{id}
	@DeleteMapping(value = "/deleteComp/{id}")
	public List<User> deleteCompany(@PathVariable long id) throws ExistEx {
		Optional<User> user = adminService.getUserById(id);
		if (user.get().getRole().equals(Role.Company)) {
			return adminService.deleteUserById(id);
		} else {
			throw new ExistEx("Id is not exist");
		}
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

	// http://localhost:8080/admin/companyUpdate
	@PutMapping(value = "/companyUpdate/{id}")
	public User updateCompany1(@PathVariable long id, @RequestBody User company) {
		adminService.updateUser(id, company);
		return company;
	}

	// http://localhost:8080/admin/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{userId}")
	public List<Coupon> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId) {
		adminService.addCouponToUser(userId, couponId);
		List<Coupon> coupons = (List<Coupon>) adminService.getAllcouponsByUserId(userId);
		return coupons;

	}

	// http://localhost:8080/admin/getCompCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public Collection<Coupon> getCompCoupons(@PathVariable long id) throws ExistEx {
		Optional<User> user = findById(id);
		if (user.get().getRole().equals(Role.Company)) {
			return adminService.getAllcouponsByUserId(id);
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
		adminService.updateCoupon(coupon);

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
