package com.talch.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Company;

@Repository
public interface CompanyRepostory extends JpaRepository<Company, Long>{
	

	@Override
	 Optional<Company> findById(Long id) ;
//	public Company getCompany(long id) throws SQLException, InterruptedException, ExistEx, SizeEx;
//
//	// get all Company's 
//	public Collection<Company> getAllCompany() throws SQLException, InterruptedException, SizeEx;
//
//	// get all Coupons of this Company
//	public Collection<Coupon> getCoupons(Company company) throws SQLException, InterruptedException, DateProblem, ExistEx, SizeEx;
//
//	//login to Company
//	public boolean login(String compname, String pass) throws SQLException, InterruptedException, SizeEx;

}

