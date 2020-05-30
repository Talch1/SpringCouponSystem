package com.talch;

import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;
import com.talch.facade.CustomerFacade;
import com.talch.facade.Facade;
import com.talch.repo.UserRepository;
import com.talch.rest.CustomSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class CouponSystem {

    private final AdminFacade admin;

    private final CompanyFacade company;

    private final CustomerFacade customer;

    private final DailyCouponExpirationTask dayli;

    private final UserRepository userRepository;

    @PostConstruct
    public void start() {
        dayli.start();
    }

    public Facade login(String userName, String password, Role role) throws Exception {
        switch (role) {
            case Admin:
                if (userName.equals("admin") && password.equals("1234"))
                    return admin;
            case Customer:
                Optional<User> cust = userRepository.findByUserNameAndPassword(userName, password);
                if ((userRepository.findByUserNameAndPassword(
                        userName, password).get().getRole().equals(Role.Customer))) {
                    customer.setCustName(cust.get().getUserName());
                    customer.setId(cust.get().getId());
                    return customer;
                }
            case Company:
                Optional<User> comp = userRepository.findByUserNameAndPassword(userName, password);
                if ((userRepository.findByUserNameAndPassword(userName, password).
                        get().getRole().equals(Role.Company))) {
                    company.setId(comp.get().getId());
                    company.setName(comp.get().getUserName());
                    return company;
                }
        }
        throw new Exception("Invalid User");

    }
}
