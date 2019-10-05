package com.talch.rest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	
	// http://localhost:8080/company/addCouponToComp
		@PutMapping(value = "/addCouponToComp/{compId}")
		public List<Coupon> addtoCompCompany(@PathVariable long compId, @RequestBody long coupId) {
			return companyService.addCoupons(compId, coupId);
		}

		// http://localhost:8080/company/createCoupon
		@PostMapping(value = "/createCoupon/{compId}")
		public Coupon insertCoup(@RequestBody Coupon coupon,@PathVariable long compId) {
			companyService.addCoupon(coupon);
			return coupon;

		}

		// http://localhost:8080/company/getCouponByID/{id}
		@GetMapping(value = "/getCouponByID/{id}")
		Optional<Coupon> findById2(@PathVariable long id) {
			return companyService.findCoupById(id);
		}

		// http://localhost:8080/company/getAllCoupons
				@GetMapping(value = "/getAllCoupons/{compId}")
				public List<Coupon> getAllCoupons(@PathVariable long compId) {
					return companyService.findAllCoup(compId);
				}

		// http://localhost:8080/company/deleteCoupon/{id}
		@DeleteMapping(value = "/deleteCoupon/{id}")
		public List<Coupon> deleteCoup(@PathVariable long id) {
			return companyService.deleteCoupon(id);
		}

		// http://localhost:8080/company/deleteCoupon/All
		@DeleteMapping(value = "/deleteCoupon/All/{compid}")
		public String deleteCoupons(@PathVariable long compid) {
			companyService.deleteCoupons(compid);
			return "All Companyes deleted";
		}

		// http://localhost:8080/company/couponUpdate
		@PutMapping(value = "/couponUpdate")
		public Optional<Company> updateCoupon( @RequestBody Coupon coupon) {
			companyService.updateCoupon( coupon);

			return companyService.findById(coupon.getId());
		}

		// http://localhost:8080/company/getAllCouponByType
		@GetMapping(value = "/getAllCouponByType/{compId}/{type}")
		public List<Coupon> getAllCouponsByType(@PathVariable  long compId ,@PathVariable CouponType type) {
			return companyService.getCouponByType(type,compId);

		}

		// http://localhost:8080/company/getAllCouponByDate
		@GetMapping(value = "/getAllCouponByDate/{compId}/{date}")
		public List<Coupon> getAllCouponsByDate(@PathVariable  long compId,@PathVariable Date date) {
			return companyService.getCouponByDate(date,compId);

		}

		// http://localhost:8080/company/getAllCouponByPrice
		@GetMapping(value = "/getAllCouponByPrice/{compId}/{price1}")
		public List<Coupon> getCouponWhenPriceBetwenPrice(@PathVariable  long compId,@PathVariable Double price1) {
			return companyService.getCouponWhenPriceBetwenPrice(price1,compId);
		}
	}



