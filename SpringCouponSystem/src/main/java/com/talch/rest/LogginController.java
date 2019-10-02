
 package com.talch.rest;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
 
import com.talch.beans.Credantions;
import com.talch.service.AdminService;
import com.talch.service.CompanyService;
import com.talch.service.CustomerService;

 @RestController
 @RequestMapping("/login/")
 public class LogginController {
	@Autowired
	CompanyService companyService;
@Autowired
	CustomerService customerService;
@Autowired
AdminService adminService;
 

// http://localhost:8080/login/logging
	
	@PostMapping(value = "/logging/")
	public boolean Loggin(@RequestBody Credantions credantions) {

	if (credantions.getType().equals(companyService.getClientType())) {

		return companyService.loggin(credantions.getUserName(), credantions.getPassword(), credantions.getType());
	} else if (credantions.getType().equals(customerService.getClientType())) {
		return customerService.loggin(credantions.getUserName(), credantions.getPassword(), credantions.getType());
		} else if (credantions.getType().equals(adminService.getClientType())) {
			return adminService.loggin(credantions.getUserName(), credantions.getPassword(), credantions.getType());
		}
		return false;
	}
}

