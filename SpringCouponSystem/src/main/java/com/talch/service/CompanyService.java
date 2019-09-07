package com.talch.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.repo.CompanyRepostory;

@Service
@Transactional
public class CompanyService {

	@Autowired
	CompanyRepostory companyRepostory;
	

	public Company addCompanyToCompany(Company company) {
		return companyRepostory.save(company);
	}

	public List<Company> deleteCompanyfromCompany(Long id) {
		companyRepostory.deleteById(id);
		return findAll();
	}

	public Company updateCompany(Long id, Company company) {
		Company compToUpdate = companyRepostory.getOne(id);
		compToUpdate.setCompName(company.getCompName());
		compToUpdate.setEmail(company.getEmail());
		compToUpdate.setPassword(company.getPassword());
		companyRepostory.save(compToUpdate);

		return compToUpdate;

	}

	public Optional<Company> findById(Long id) {
		return companyRepostory.findById(id);
	}

	public List<Company> findAll() {
		return companyRepostory.findAll();
	}

	//public List<Company> findAllByCoupon(Coupon coupons) {
	//	return companyRepostory.findAllByCoupon(coupons);
	//}

}
