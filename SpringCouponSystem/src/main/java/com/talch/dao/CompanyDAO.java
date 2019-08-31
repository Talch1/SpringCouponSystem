package com.talch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.talch.repo.CompanyRepository;

@Repository
public class CompanyDAO {
	@Autowired
	private CompanyRepository companyRepository;

}
