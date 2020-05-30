package com.talch.utils;

import com.talch.CouponSystem;
import com.talch.beans.Role;
import com.talch.repo.UserRepository;
import com.talch.rest.CustomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class Utils {

    private final CouponSystem system;

    private final UserRepository userRepository;

    private final ResponseEntity<String> responseEntitySesionNull = ResponseEntity.
            status(HttpStatus.BAD_REQUEST).body("Session is null");
    private final ResponseEntity responseEntitySomesingWrong = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Somesing Wrong");

    public CustomSession isActive(String token) {
        CustomSession customSession = system.getTokensMap().get(token);
        if (customSession != null) {
            customSession.setLastAccessed(System.currentTimeMillis());
            return customSession;
        }
        return null;
    }

    public boolean checkRole(CustomSession session, Role role) {
        if ((session != null) && (session.getFacade().getRole().equals(role))) {
            return true;
        }
        return false;
    }
}