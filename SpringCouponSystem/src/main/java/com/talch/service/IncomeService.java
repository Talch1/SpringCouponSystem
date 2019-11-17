package com.talch.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.repo.IncomeRepository;

public class IncomeService {

	@Autowired
	IncomeRepository incomeRepository;

	public void storeIncome(Income income) {
		incomeRepository.save(income);
	}

	public Collection<Income> viewAllIncome() {
		return incomeRepository.findAll();

	}

	public Collection<Income> vievIncomeByCustomer(long customerId) {
		List<Income> allIncomes = incomeRepository.findAll();
		List<Income> custIncomes = new ArrayList<Income>();
		for (Income income : allIncomes) {
			if (income.getRole() == Role.Customer) {
				custIncomes.add(income);
			}
		}
		return custIncomes;

	}

	public Collection<Income> vievIncomeByCompany(long companyId) {
		List<Income> allIncomes = incomeRepository.findAll();
		List<Income> compIncomes = new ArrayList<Income>();
		for (Income income : allIncomes) {
			if (income.getRole() == Role.Company) {
				compIncomes.add(income);
			}
		}
		return compIncomes;

	}

}
