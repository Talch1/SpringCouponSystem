package com.talch.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Company;


@Repository
public interface CompanyRepostory extends JpaRepository<Company, Long> {

	public Optional<Company> findById(Long id);
	
	List<Company> findAll();


	// get all coupons of this company

}
