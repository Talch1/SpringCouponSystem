
package com.talch.rest;

import com.talch.CouponSystem;
import com.talch.beans.Role;
import com.talch.facade.Facade;
import com.talch.service.LogginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
public class LogginController {

    private final CouponSystem system;

    private final LogginService logginService;

    //http://localhost:8081/v1/login/logging
    @PostMapping(value = "/logging/{username}/{password}/{type}")
    public ResponseEntity<?> login(@PathVariable("username") String userName, @PathVariable("password")
            String password,@PathVariable("type") String type) {
        return  logginService.login(userName,password,type);
    }
}
