package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.ClientType;
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

// http://localhost:8080/company/logging
	@GetMapping(value = "/logging")
	public boolean Loggin(@PathVariable String name, @PathVariable String password,
			@PathVariable ClientType clientType) {

		if (clientType.equals(companyService.getClientType())) {

			return companyService.loggin(name, password, clientType);
		} else if (clientType.equals(customerService.getClientType())) {
			return customerService.loggin(name, password, clientType);
		} else if (clientType.equals(adminService.getClientType())) {
			return adminService.loggin(name, password, clientType);
		}
		return false;
	}
}