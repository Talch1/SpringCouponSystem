package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.ClientType;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.repo.CompanyRepostory;
import com.talch.repo.CouponRepository;

@Service
@Transactional
public class CompanyService {

	@Autowired
	CompanyRepostory companyRepostory;
	@Autowired
	CouponRepository couponRepository;
	
	ClientType clientType = ClientType.Company;
	

	public String getClientType() {
		return clientType.name();
	}


	public List<Coupon> addCompanyToCompany(Company company) {
		 companyRepostory.save(company);
		 return findAllCoup(company.getId());
	}

	public List<Company> deleteCompanyfromCompany(long id) {
		companyRepostory.deleteById(id);
		return companyRepostory.findAll();
	}

	public List<Company> insertComp(Company company) {
		companyRepostory.save(company);
		return companyRepostory.findAll();

	}

	public Company updateCompany(long id, Company company) {
		Company compToUpdate = companyRepostory.getOne(id);
		compToUpdate.setCompName(company.getCompName());
		compToUpdate.setEmail(company.getEmail());
		compToUpdate.setPassword(company.getPassword());
		companyRepostory.save(compToUpdate);
		return compToUpdate;

	}

	public String deleteCompanys() {
		companyRepostory.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Company> findById(long id) {
		return companyRepostory.findById(id);
	}

	public List<Company> findAll() {
		return companyRepostory.findAll();
	}

	public List<Coupon> addCoupons(long id, long coupId) {
		Company compToUpdate = companyRepostory.getOne(id);
		List<Coupon> list= (List<Coupon>) compToUpdate.getCupons();
		Coupon coupon = couponRepository.getOne(coupId);
		list.add(coupon);
		compToUpdate.setCupons(list);
		companyRepostory.save(compToUpdate);
		return findAllCoup(id);
	}
	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}

	public List<Coupon> deleteCoupon(long id) {
		couponRepository.deleteById(id);
		return couponRepository.findAll();
	}

	public Coupon updateCoupon(Coupon coupon) {		
		couponRepository.save(coupon);
		return coupon;
	}

	public String deleteCoupons(long id) {
		Company company = companyRepostory.getOne(id);
		List<Coupon> coupons = null;
		company.setCupons(coupons);
		companyRepostory.save(company);
		
	//	couponRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Coupon> findCoupById(Long id) {
		
		return couponRepository.findById(id);
	}

	public List<Coupon> findAllCoup(Long compId) {
		Company company	= companyRepostory.getOne(compId);
		List<Coupon> coupons= (List<Coupon>) company.getCupons();
		return coupons;
	}

	public List<Coupon> getCouponByType(CouponType type,long compId) {	
		Optional<Company> company	= companyRepostory.findById(compId);
		List<Coupon> coupons= (List<Coupon>) company.get().getCupons();
		List<Coupon> byType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getType().equals(type)) {
				byType.add(coupon);
			}
		}
		return byType;
	}

	public List<Coupon> getCouponByDate(Date date,long compId) {

		
		Optional<Company> company	= companyRepostory.findById(compId);
		List<Coupon> coupons= (List<Coupon>) company.get().getCupons();
		List<Coupon> byDate = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(date)) {
				byDate.add(coupon);
			}
		}
		return byDate;
	
	}
	public Company getCompanyByNameAndPass(String name,String pass) {
		return companyRepostory.findByCompNameAndPassword(name, pass);
	}

	public List<Coupon> getCouponWhenPriceBetwenPrice(Double price1,long compId) {
		
		//return couponRepository.findByPriceLessThan(price1);
		Optional<Company> company	= companyRepostory.findById(compId);
		List<Coupon> coupons= (List<Coupon>) company.get().getCupons();
		List<Coupon> byPrice = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice()<price1) {
				byPrice.add(coupon);
			}
		}
		return byPrice;
	}

	public boolean loggin(String compName, String password,String c) {

		if ((companyRepostory.existsById((companyRepostory.findByCompNameAndPassword(compName, password).getId())))&&(c.equals(ClientType.Company.name()))) {
			return true;
		} else {
			return false;
		}

	}

	
}