package com.talch.service;

import com.talch.beans.Role;
import com.talch.facade.Facade;
import com.talch.rest.CustomSession;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springfox.documentation.spi.service.contexts.ApiListingContext;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogginService {

    private final Utils utils;
    private final CouponSystem couponSystem;
    private final  CustomSession customSession;
    private final ApplicationContext context;

    public ResponseEntity<?> login(String userName, String password, String type) throws Exception {
        if (!type.equals("Admin") && !type.equals("Company") && !type.equals("Customer")) {
            return new ResponseEntity<>("Wrong type", HttpStatus.UNAUTHORIZED);
        }
        CustomSession session = context.getBean(CustomSession.class);
        Facade facade = null;
        String token = UUID.randomUUID().toString();
        long lastAccessed = System.currentTimeMillis();
        facade = couponSystem.login(userName, password, Role.valueOf(type));
        if (facade != null) {
            session.setFacade(facade);
            session.setLastAccessed(lastAccessed);
            utils.getTokensMap().put(token, session);

            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return utils.getResponseEntitySesionNull();
    }
}
