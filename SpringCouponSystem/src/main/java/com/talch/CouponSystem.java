package com.talch;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;
import com.talch.facade.CustomerFacade;
import com.talch.facade.Facade;
import com.talch.repo.UserRepository;
import com.talch.rest.CustomSession;

import lombok.Data;

@Service
@Data
public class CouponSystem {

	@Autowired
	private AdminFacade admin;
	
	@Autowired
	private CompanyFacade company;
	
	@Autowired
	private CustomerFacade customer;
	
	@Autowired
	private DailyCouponExpirationTask dayli;

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private Map<String, CustomSession> tokensMap;
	
	@PostConstruct
	public void start() {
		dayli.start();
	}

	public Facade login(String userName, String password, Role role) throws ExistEx {
		switch (role) {
		case Admin:
			if (userName.equals("admin") && password.equals("1234"))
				return admin;
		case Customer:
			User cust = userRepository.findByUserNameAndPassword(userName, password);
			if ((cust != null)
					&& (userRepository.findByUserNameAndPassword(userName, password).getRole().equals(Role.Customer))) {
				customer.setCustName(cust.getUserName());
				customer.setCustId(cust.getId());
				return customer;
			}
		case Company:
			User comp = userRepository.findByUserNameAndPassword(userName, password);
			if ((comp != null)
					&& (userRepository.findByUserNameAndPassword(userName, password).getRole().equals(Role.Company))) {
				company.setCompId(comp.getId());
				company.setCompName(comp.getUserName());
				return company;
			}
		}
		throw new ExistEx("Invaled User");

	}
}
