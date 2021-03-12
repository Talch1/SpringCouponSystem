
package com.talch.rest;

import com.talch.service.LogginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
@CrossOrigin("*")
public class LogginController {

    private final LogginService logginService;

    //http://localhost:8081/v1/login/logging
    @PostMapping(value = "/logging/{username}/{password}/{type}")
    public ResponseEntity<?> login(@PathVariable("username") String userName, @PathVariable("password")
            String password, @PathVariable("type") String type) {
        try {
            return logginService.login(userName, password, type);
        } catch (Exception e) {
            e.printStackTrace();
        }return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some wrong ");
    }
}


