package com.talch.service;

import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.repo.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public void storeIncome(Income income) {
        incomeRepository.save(income);
    }

    public Collection<Income> viewAllIncome() {
        return incomeRepository.findAll();
    }

    public Collection<Income> vievIncomeByCustomer(long custId) {
        List<Income> allIncomes = incomeRepository.findAll();
        List<Income> custIncomes = new ArrayList<Income>();
        for (Income income : allIncomes) {
            if (income.getRole().equals(Role.Customer) && (income.getUserId() == custId)) {
                custIncomes.add(income);
            }
        }
        return custIncomes;
    }

    public Collection<Income> vievIncomeByCompany(long compId) {
        List<Income> allIncomes = incomeRepository.findAll();
        List<Income> compIncomes = new ArrayList<Income>();
        for (Income income : allIncomes) {
            if ((income.getRole().equals(Role.Company)) && income.getUserId() == compId) {
                compIncomes.add(income);
            }
        }
        return compIncomes;
    }
}
