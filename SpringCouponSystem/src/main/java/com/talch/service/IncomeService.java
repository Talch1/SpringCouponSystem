package com.talch.service;

import com.talch.DailyCouponExpirationTask;
import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.facade.AdminFacade;
import com.talch.facade.CompanyFacade;
import com.talch.facade.CustomerFacade;
import com.talch.facade.Facade;
import com.talch.repo.IncomeRepository;
import com.talch.repo.UserRepository;
import com.talch.utils.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    private final Utils utils;

    public void storeIncome(Income income) {
        incomeRepository.save(income);
    }

    public ResponseEntity<?> viewAllIncome(String token) {
        if (utils.checkRole(utils.getTokensMap().get(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK).body(incomeRepository.findAll());
        } else return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity<?> vievIncomeByCustomer(String token, long custId) {
        if (utils.checkRole(utils.getTokensMap().get(token), Role.Admin)) {
            List<Income> custIncomes = incomeRepository.findAll().stream()
                    .filter(income -> (((income.getRole().equals(Role.Customer)) && (income.getId() == custId))))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(custIncomes);
        } else return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity<?> vievIncomeByCompany(String token) {
        if (utils.checkRole(utils.getTokensMap().get(token), Role.Company)) {
            List<Income> compIncomes = incomeRepository.findAll().stream()
                    .filter(income -> ((income.getRole().equals(Role.Company)) &&
                            (income.getUserId() == utils.getTokensMap().get(token).getFacade().getId())))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(compIncomes);
        } else return utils.getResponseEntitySesionNull();
    }

}
