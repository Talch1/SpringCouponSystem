package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.service.CustomerService;

@RestController
@RequestMapping("/customer/")
public class CustomerController {
	@Autowired
	CustomerService customerService;

}
