package com.talch.service;

import com.talch.beans.Role;
import com.talch.facade.Facade;
import com.talch.rest.CustomSession;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogginService {

    private final Utils utils;

    public ResponseEntity login(String userName, String password, String type) {
        if (!type.equals("Admin") && !type.equals("Company") && !type.equals("Customer")) {
            return new ResponseEntity<>("Wrong type", HttpStatus.UNAUTHORIZED);
        }
        CustomSession session = new CustomSession();
        Facade facade = null;
        String token = UUID.randomUUID().toString();
        long lastAccessed = System.currentTimeMillis();
        facade = utils.getSystem().login(userName, password, Role.valueOf(type));
        if (facade != null) {
            session.setFacade(facade);
            session.setLastAccessed(lastAccessed);
            utils.getSystem().getTokensMap().put(token, session);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return utils.getResponseEntitySesionNull();
    }
}
