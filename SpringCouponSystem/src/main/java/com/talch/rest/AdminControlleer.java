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
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Customer;
import com.talch.service.AdminService;

@RestController
@RequestMapping("/admin/")
public class AdminControlleer {

	@Autowired
	AdminService adminService;

//******************************Customer**********************************

	// http://localhost:8080/admin/custCreate
	@PostMapping(value = "/customerCreate")
	public List<Customer> insertCu(@RequestBody Customer customer) {
		adminService.insertCust(customer);
		return adminService.findAllCust();

	}

	// http://localhost:8080/admin/getByID/{id}
	@GetMapping("/getCustByID/{id}")
	Optional<Customer> findById(@PathVariable Long id) {
		return adminService.findById(id);
	}

	// http://localhost:8080/admin/getCustomers
	@GetMapping(value = "/getCustomers")
	public List<Customer> getAllCustomers() {
		return adminService.findAllCust();
	}

	// http://localhost:8080/admin/delete/{id}
	@DeleteMapping(value = "/deleteCust/{id}")
	public List<Customer> deleteCustomer(@PathVariable Long id) {
		return adminService.deleteCustomer(id);

	}

	// http://localhost:8080/admin/delete/All
	@DeleteMapping(value = "/deleteCust/All")
	public String deleteCustomers() {
		return adminService.deleteCustomers();

	}

	// http://localhost:8080/admin/custUpdate
	@PutMapping(value = "/custUpdate/{id}")
	public Customer updateCustomer1(@PathVariable Long id, @RequestBody Customer customer) {
		adminService.updateCustomer(id, customer);
		return customer;
	}

	// http://localhost:8080/admin/addCouponToCust
	@PutMapping(value = "/addCouponToCust/{id}")
	public String addCoupon(@PathVariable Long id, @RequestBody List<Coupon> coupons) {
		adminService.purchoiseCoupon(id, coupons);
		return "Coupons Added";
	}

	// http://localhost:8080/admin/getCustCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public Collection<Coupon> getCustCoupons(@PathVariable Long id) {
		return adminService.getAllcouponsByCustId(id);

	}
	// ***********************Company***************************************

	// http://localhost:8080/admin/compnyCreate
	@PostMapping(value = "/compnyCreate")
	public List<Company> insertCompany(@RequestBody Company company) {
		adminService.insertCompany(company);
		return adminService.insertCompany(company);

	}

	// http://localhost:8080/admin/getByID/{id}
	@GetMapping(value = "/getCompByID/{id}")
	Optional<Company> findById1(@PathVariable Long id) {
		return adminService.geCompany(id);
	}

	// http://localhost:8080/admin/getCompanys
	@GetMapping(value = "/getCompanys")
	public List<Company> getAllCompanys() {
		return adminService.findAllCom();
	}

	// http://localhost:8080/admin/deleteComp/{id}
	@DeleteMapping(value = "/deleteComp/{id}")
	public List<Company> deleteCompany(@PathVariable Long id) {
		return adminService.deleteCompanyfromCompany(id);

	}

	// http://localhost:8080/admin/deleteComp/All
	@DeleteMapping(value = "/deleteComp/All")
	public String deleteCompany() {
		adminService.deleteCompanys();
		return "All Companyes deleted";
	}

	// http://localhost:8080/admin/companyUpdate
	@PutMapping(value = "/companyUpdate/{id}")
	public List<Company> updateCompany1(@PathVariable Long id, @RequestBody Company company) {
		adminService.updateCompany(id, company);
		return adminService.findAllCom();
	}

	// http://localhost:8080/admin/addCouponToComp
	@PutMapping(value = "/addCouponToComp/{id}")
	public String addCompany(@PathVariable Long id, @RequestBody List<Coupon> coupons) {
		adminService.addCoupons(id, coupons);
		return "Coupons Added";
	}

	// http://localhost:8080/admin/getCompCoupons
	@GetMapping(value = "/getCompCoupons/{id}")
	public Collection<Coupon> getCompCoupons(@PathVariable Long id) {
		return adminService.getAllcouponsByCompId(id);

	}

	// ***********************Coupon*****************************************

	// http://localhost:8080/admin/createCoup
	@PostMapping(value = "/createCoup")
	public List<Coupon> insertCoup(@RequestBody Coupon coup) {
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
	public String deleteCoupons() {
		adminService.deleteCoupons();
		return "All Companyes deleted";
	}

	// http://localhost:8080/admin/coupUpdate
	@PutMapping(value = "/coupUpdate/{id}")
	public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
		adminService.updateCoupon(id, coupon);

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
