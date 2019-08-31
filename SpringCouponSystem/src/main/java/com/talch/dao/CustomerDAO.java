package com.talch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.talch.repo.CustomerRepository;

@Repository
public class CustomerDAO {
	@Autowired
	private CustomerRepository customerRepository;

}
