package com.talch.rest;

import com.talch.CouponSystem;
import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;
import com.talch.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/income")
public class IncomeController {

    private final IncomeService incomeService;

    private final CouponSystem system;

    private CustomSession isActive(String token) {
        return system.getTokensMap().get(token);
    }

    //http://localhost:8081/v1/income/store
    @PostMapping(value = "/store")
    public void storeIncome(@RequestBody Income income) {
        incomeService.storeIncome(income);
    }

    //http://localhost:8081/v1/income/allIncome
    @GetMapping(value = "/anllIncome")
    public ResponseEntity<?> viewAllIncome(@RequestHeader String token) {
        CustomSession customSession = isActive(token);

        if (customSession != null) {
            customSession.setLastAccessed(System.currentTimeMillis());
            AdminFacade facade = (AdminFacade) customSession.getFacade();
            if (facade.getRole().equals(Role.Admin)) {
                return ResponseEntity.status(HttpStatus.OK).body(incomeService.viewAllIncome());
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //http://localhost:8081/v1/income/allCustIncome
    @GetMapping(value = "/allCustIncome/{custId}")
    public ResponseEntity<?> vievIncomeByCustomer(@PathVariable long custId, @RequestHeader String token) {
        CustomSession customSession = isActive(token);

        if (customSession != null) {
            customSession.setLastAccessed(System.currentTimeMillis());
            AdminFacade facade = (AdminFacade) customSession.getFacade();
            if (facade.getRole().equals(Role.Admin)) {
                return ResponseEntity.status(HttpStatus.OK).body(incomeService.vievIncomeByCustomer(custId));
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //http://localhost:8081/v1/income/allCompIncome
    @GetMapping(value = "/allCompIncome")
    public ResponseEntity<?> vievIncomeByCompany(@RequestHeader String token) {
        CustomSession customSession = isActive(token);

        if (customSession != null) {
            customSession.setLastAccessed(System.currentTimeMillis());
            CompanyFacade facade = (CompanyFacade) customSession.getFacade();
            if (facade.getRole().equals(Role.Company)) {
                long compId = facade.getCompId();
                System.out.println(compId);
                return ResponseEntity.status(HttpStatus.OK).body(incomeService.vievIncomeByCompany(compId));
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
