package com.talch.facade;

import org.springframework.stereotype.Component;

import com.talch.beans.Role;

import lombok.Data;

@Data
@Component
public class CompanyFacade extends Facade {

	Role role = Role.Company;
	long compId;
	String compName;
}
