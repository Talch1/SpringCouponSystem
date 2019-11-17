package com.talch.facade;

import org.springframework.stereotype.Component;

import com.talch.beans.Role;
import lombok.Data;

@Data
@Component
public class CustomerFacade extends Facade {

	private long custId;
	private String custName;
	private Role role = Role.Customer;

}
