package com.talch.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Company;
import com.talch.beans.Coupon;

@Repository
public interface CompanyRepostory extends JpaRepository<Company, Long> {

	public Optional<Company> findById(Long id);
	
	List<Company> findAll();

	//List<Company> findAllByCoupon(Coupon coupons);

}
