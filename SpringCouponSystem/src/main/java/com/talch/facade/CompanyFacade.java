package com.talch.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.talch.beans.ClientType;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.exeption.LogginEx;
import com.talch.repo.CouponRepository;
import com.talch.service.CompanyService;

public class CompanyFacade implements CouponClientFacade {
	@Autowired
	CompanyService companyService;

	

	public void createCompany(Company company) {
		companyService.insertComp(company);
	}

	public void removeCompanys() {
		companyService.deleteCompanys();
	}

	public void removeCompany(Company company) {
		companyService.deleteCompanyfromCompany(company.getId());
	}

	public void updateCompany(Company company, long id) {
		companyService.updateCompany(id, company);
	}

	public Optional<Company> getCompany(long id) {

		return companyService.findById(id);
	}

	public Collection<Company> getAllCompanyes() {
		return companyService.findAll();
	}

	public Collection<Coupon> getAllcouponsByCompId(long id) {
		Optional<Company> company = companyService.findById(id);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		return coupons;

	}

	public void createCoupon(Coupon coupon) {
		companyService.addCoupon(coupon);
	}

	public void removeCoupon(long id) {
		companyService.deleteCoupon(id);
	}

	public void removeAllCoupon() {
		companyService.deleteCoupons();
	}

	
	public Coupon updateCoupon(Coupon coupon,long id)  {
		return companyService.updateCoupon(id, coupon);
	}

	
	public Optional<Coupon> getCoupon(long id) {
		return companyService.findCoupById(id);
	}

	
	public List<Coupon> getAllCoupons(){
		return companyService.findAllCoup();
	}
	
	public List<Coupon> getAllCouponsByComp(Company company){
		Optional<Company> company1 = companyService.findById(company.getId());
		List<Coupon> coupons = (List<Coupon>) company1.get().getCupons();
		return coupons;
	}
	

	
	public List<Coupon> getCouponByType(CouponType type){
		return companyService.getCouponByType(type);

	}

	
	public List<Coupon> getCouponByPrice(double price){
	return companyService.getCouponWhenPriceBetwenPrice(price);
	}

	
	public List<Coupon> getCouponBeforeDate(Date date){
			return companyService.getCouponByDate(date);
			

	}

	
	public CouponClientFacade login(String name, String password, ClientType c) {
		return null;
//			throws  LogginEx, {
//		
//		{
//			if (companyDBDAO.login(name, password)) {
//				CompanyFacade companyFacade = new CompanyFacade();
//				return companyFacade;
//			} else {
//				throw new LogginEx("Invalid email or password");
//			}
//
//		}
//	}
//
}

}
