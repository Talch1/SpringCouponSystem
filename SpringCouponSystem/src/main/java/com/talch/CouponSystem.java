package com.talch;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;
import com.talch.facade.CustomerFacade;
import com.talch.facade.Facade;
import com.talch.repo.UserRepository;

@Service
public class CouponSystem {

	@Autowired
	private AdminFacade admin;

	@Autowired
	private UserRepository userRepository;

	public Facade login(String userName, String password, Role role) throws ExistEx {
		switch (role) {
		case Admin:
			if (userName.equals("admin") && password.equals("1234"))
				return admin;
		case Customer:
			User cust = userRepository.findByUserNameAndPassword(userName, password);
			if ((cust != null)
					&& (userRepository.findByUserNameAndPassword(userName, password).getRole().equals(Role.Customer))) {
				CustomerFacade customer = new CustomerFacade();
				customer.setCustName(cust.getUserName());
				customer.setCustId(cust.getId());
				return customer;
			}
		case Company:
			User comp = userRepository.findByUserNameAndPassword(userName, password);
			if ((comp != null)
					&& (userRepository.findByUserNameAndPassword(userName, password).getRole().equals(Role.Company))) {
				CompanyFacade company = new CompanyFacade();
				company.setCompId(comp.getId());
				company.setCompName(comp.getUserName());
				return company;
			}
		}
		throw new ExistEx("Invaled User");

	}
}
