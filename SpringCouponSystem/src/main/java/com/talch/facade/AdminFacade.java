package com.talch.facade;

import org.springframework.stereotype.Component;

import com.talch.beans.Role;
@Component
public class AdminFacade extends Facade {

	Role role = Role.Admin;
	long adminId = 1;
	String adminName = "Admin";

}
