
package com.talch.rest;

import com.talch.CouponSystem;
import com.talch.beans.Role;
import com.talch.facade.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/login")
public class LogginController {

    @Autowired
    private CouponSystem system;

    //http://localhost:8081/v1/login/logging
    @PostMapping(value = "/logging/{username}/{password}/{type}")

    public ResponseEntity<?> login(@PathVariable("username") String userName, @PathVariable("password") String password,
                                   @PathVariable("type") String type) {

        if (!type.equals("Admin") && !type.equals("Company") && !type.equals("Customer")) {
            return new ResponseEntity<>("Wrong type", HttpStatus.UNAUTHORIZED);
        }
        CustomSession session = new CustomSession();
        Facade facade = null;
        String token = UUID.randomUUID().toString();

        long lastAccessed = System.currentTimeMillis();
        try {
            facade = system.login(userName, password, Role.valueOf(type));
            if (facade != null) {
                session.setFacade(facade);
                session.setLastAccessed(lastAccessed);
                system.getTokensMap().put(token, session);
                return ResponseEntity.status(HttpStatus.OK).body(token);
            } else {
                System.out.println("facade is null");
                return null;
            }

        } catch (ExistEx e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
