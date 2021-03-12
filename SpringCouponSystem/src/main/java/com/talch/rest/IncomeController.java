package com.talch.rest;

import com.talch.beans.Income;
import com.talch.service.IncomeService;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/income")
@CrossOrigin("*")
public class IncomeController {

    private final IncomeService incomeService;

    private final Utils utils;

    private CustomSession isActive(String token) {
        return utils.getTokensMap().get(token);
    }

    //http://localhost:8081/v1/income/store
    @PostMapping(value = "/store")
    public void storeIncome(@RequestBody Income income) {
        incomeService.storeIncome(income);
    }

    //http://localhost:8081/v1/income/allIncome
    @GetMapping(value = "/anllIncome")
    public ResponseEntity<?> viewAllIncome(@RequestHeader String token) {
        return incomeService.viewAllIncome(token);
    }

    //http://localhost:8081/v1/income/allCustIncome
    @GetMapping(value = "/allCustIncome/{custId}")
    public ResponseEntity<?> vievIncomeByCustomer(@PathVariable long custId, @RequestHeader String token) {
        return incomeService.vievIncomeByCustomer(token, custId);
    }

    //http://localhost:8081/v1/income/allCompIncome
    @GetMapping(value = "/allCompIncome")
    public ResponseEntity<?> vievIncomeByCompany(@RequestHeader String token) {
        return incomeService.vievIncomeByCompany(token);
    }
}
