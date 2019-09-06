package com.talch.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talch.repo.CompanyRepostory;

@Service
@Transactional
public class CompanyService {
	
@Autowired
	CompanyRepostory companyRepostory;

 
}
