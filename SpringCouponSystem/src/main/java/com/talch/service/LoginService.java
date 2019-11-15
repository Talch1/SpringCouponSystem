package com.talch.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.repo.UserRepository;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	UserRepository userRepository;

	public boolean loggin(String userName, String password, String role) {
		if ((userName.equals("admin") && password.equals("1234") && role.equals(Role.Admin.name()))
				|| (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
						&& (userRepository.findByUserNameAndPassword(userName, password).getRole())
								.equals(Role.Customer))
				|| (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
						&& (userRepository.findByUserNameAndPassword(userName, password).getRole())
								.equals(Role.Company))) {
			return true;

		}
		return false;
	}
}
