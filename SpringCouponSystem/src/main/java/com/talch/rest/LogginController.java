
package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.service.UserService;


@RestController
@RequestMapping("login")
public class LogginController {
	@Autowired
	UserService userService;
	@Autowired
	UserService adminService;

// http://localhost:8080/login/logging

	@PostMapping(value = "/logging")

	public boolean Loggin(@RequestBody User user) {

		if (user.getRole().equals(Role.Company)) {

			return userService.loggin(user.getUserName(), user.getPassword(), user.getRole().name());
		} else if (user.getRole().equals(Role.Customer)) {
			return userService.loggin(user.getUserName(), user.getPassword(), user.getRole().name());
		} else if (user.getRole().equals(Role.Admin)) {
			return userService.loggin(user.getUserName(), user.getPassword(), user.getRole().name());
		}
		return false;
	}
}
