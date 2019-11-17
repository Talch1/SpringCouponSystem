
package com.talch.rest;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.CouponSystem;
import com.talch.beans.Role;
import com.talch.exeption.ExistEx;
import com.talch.facade.Facade;
import com.talch.service.LoginService;

@RestController
@RequestMapping("login")
public class LogginController {
	@Autowired
	LoginService loginService;
	@Autowired

	private Map<String, CustomSession> tokensMap;
	@Autowired
	private CouponSystem system;

//http://localhost:8080/login/logging
	@PostMapping(value = "/logging/{username}/{password}/{type}")

	public ResponseEntity<String> login(@PathVariable("username") String userName,
			@PathVariable("password") String password, @PathVariable("type") String type) {
		if (!type.equals("Admin") && !type.equals("Company") && !type.equals("Customer")) {
			return new ResponseEntity<>("Wrong type", HttpStatus.UNAUTHORIZED);
		}
		CustomSession session = new CustomSession();
		Facade facade = null;
		String token = UUID.randomUUID().toString();

		long lastAccessed = System.currentTimeMillis();
		try {
			facade = system.login(userName, password, Role.valueOf(type));
			session.setFacade(facade);
			session.setLastAccessed(lastAccessed);
			tokensMap.put(token, session);
			return ResponseEntity.status(HttpStatus.OK).body(token);

		} catch (ExistEx e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}
