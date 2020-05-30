package com.talch.service;

import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.repo.IncomeRepository;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
