package com.talch.service;

import java.sql.Date;
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
	

	public ClientType getClientType() {
		return clientType;
	}


	public Company addCompanyToCompany(Company company) {
		return companyRepostory.save(company);
	}

	public List<Company> deleteCompanyfromCompany(Long id) {
		companyRepostory.deleteById(id);
		return companyRepostory.findAll();
	}

	public List<Company> insertComp(Company company) {
		companyRepostory.save(company);
		return companyRepostory.findAll();

	}

	public Company updateCompany(Long id, Company company) {
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

	public Optional<Company> findById(Long id) {
		return companyRepostory.findById(id);
	}

	public List<Company> findAll() {
		return companyRepostory.findAll();
	}

	public void addCoupons(long id, List<Coupon> coupons) {
		Company compToUpdate = companyRepostory.getOne(id);
		compToUpdate.setCupons(coupons);
		companyRepostory.save(compToUpdate);

	}

	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}

	public List<Coupon> deleteCoupon(Long id) {
		couponRepository.deleteById(id);
		return couponRepository.findAll();
	}

	public Coupon updateCoupon(Long id, Coupon coupon) {
		Coupon coupToUpdate = couponRepository.getOne(id);
		coupToUpdate.setEndDate(coupon.getEndDate());

		coupToUpdate.setPrice(coupon.getPrice());
		couponRepository.save(coupToUpdate);

		return coupToUpdate;

	}

	public String deleteCoupons() {
		couponRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Coupon> findCoupById(Long id) {
		return couponRepository.findById(id);
	}

	public List<Coupon> findAllCoup() {
		return couponRepository.findAll();
	}

	public List<Coupon> getCouponByType(CouponType type) {
		return couponRepository.findByType(type);
	}

	public List<Coupon> getCouponByDate(Date date) {

		return couponRepository.findByEndDateBefore(date);

	}

	public List<Coupon> getCouponWhenPriceBetwenPrice(Double price1) {
		return couponRepository.findByPriceLessThan(price1);

	}

	public boolean loggin(String compName, String password,ClientType clientType) {

		if ((companyRepostory.existsById((companyRepostory.findByCompNameAndPassword(compName, password).getId())))&&(clientType.toString().equals(ClientType.Company.toString()))) {
			return true;

		} else {
			return false;
		}

	}

	
}