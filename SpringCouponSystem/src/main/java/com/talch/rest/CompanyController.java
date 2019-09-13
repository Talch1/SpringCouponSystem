package com.talch.rest;

import java.sql.Date;
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
import com.talch.service.CompanyService;

@RestController
@RequestMapping("company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	// http://localhost:8080/company/compCreate
	@PostMapping(value = "/compCreate")
	public List<Company> insertC(@RequestBody Company company) {
		companyService.insertComp(company);
		return companyService.findAll();

	}

	// http://localhost:8080/company/getByID/{id}
	@GetMapping(value = "/getCompByID/{id}")
	Optional<Company> findById(@PathVariable Long id) {
		return companyService.findById(id);
	}

	// http://localhost:8080/company/getCompanys
	@GetMapping(value = "/getCompanys")
	public List<Company> getAllCompanys() {
		return companyService.findAll();
	}

	// http://localhost:8080/company/delete/{id}
	@DeleteMapping(value = "/deleteComp/{id}")
	public List<Company> deleteCompany(@PathVariable Long id) {
		return companyService.deleteCompanyfromCompany(id);

	}

	// http://localhost:8080/company/delete/All
	@DeleteMapping(value = "/deleteComp/All")
	public String deleteCompany() {
		companyService.deleteCompanys();
		return "All Companyes deleted";
	}

	// http://localhost:8080/company/compUpadte
	@PutMapping(value = "/compUpdate/{id}")
	public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
		companyService.updateCompany(id, company);
		return company;
	}
	
	// http://localhost:8080/company/addCouponToComp
		@PutMapping(value = "/addCouponToComp/{id}")
		public String addCompany(@PathVariable Long id, @RequestBody List<Coupon> coupons) {
			companyService.addCoupons(id, coupons);
			return "Coupons Added";
		}

		// http://localhost:8080/company/createCoup
		@PostMapping(value = "/createCoupon")
		public List<Coupon> insertCoup(@RequestBody Coupon coup) {
			companyService.addCoupon(coup);
			return companyService.findAllCoup();

		}

		// http://localhost:8080/company/getCoupByID/{id}
		@GetMapping(value = "/getCouponByID/{id}")
		Optional<Coupon> findById2(@PathVariable Long id) {
			return companyService.findCoupById(id);
		}

		// http://localhost:8080/company/getCoupons
		@GetMapping(value = "/getAllCoupons")
		public List<Coupon> getAllCoupons() {
			return companyService.findAllCoup();
		}

		// http://localhost:8080/company/deleteCoup/{id}
		@DeleteMapping(value = "/deleteCoupon/{id}")
		public List<Coupon> deleteCoup(@PathVariable Long id) {
			return companyService.deleteCoupon(id);
		}

		// http://localhost:8080/company/deleteCoup/All
		@DeleteMapping(value = "/deleteCoupon/All")
		public String deleteCoupons() {
			companyService.deleteCoupons();
			return "All Companyes deleted";
		}

		// http://localhost:8080/company/coupUpdate
		@PutMapping(value = "/couponUpdate/{id}")
		public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
			companyService.updateCoupon(id, coupon);

			return coupon;

		}

		// http://localhost:8080/company/getAllCoupByType
		@GetMapping(value = "/getAllCouponByType/{type}")

		public List<Coupon> getAllCouponsByType(@PathVariable CouponType type) {
			return companyService.getCouponByType(type);

		}

		// http://localhost:8080/company/getAllCoupByDate
		@GetMapping(value = "/getAllCouponByDate/{date}")
		public List<Coupon> getAllCouponsByDate(@PathVariable Date date) {
			return companyService.getCouponByDate(date);

		}

		// http://localhost:8080/company/getAllCoupByPrice
		@GetMapping(value = "/getAllCouponByPrice/{price1}")
		public List<Coupon> getCouponWhenPriceBetwenPrice(@PathVariable Double price1) {
			return companyService.getCouponWhenPriceBetwenPrice(price1);
		}
	}



