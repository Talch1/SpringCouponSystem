package com.talch.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.Income;
import com.talch.facade.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	IncomeService incomeService;

//http://localhost:8080/income/store
	@PostMapping(value = "/store")
	public void storeIncome(@RequestBody Income income) {
		incomeService.storeIncome(income);
	}

//http://localhost:8080/income/allIncome
	@GetMapping(value = "/allIncome")
	public Collection<Income> viewAllIncome() {

		return incomeService.viewAllIncome();
	}

//http://localhost:8080/income/allCustIncome
	@GetMapping(value = "/allCustIncome")
	public Collection<Income> vievIncomeByCustomer() {
		return incomeService.vievIncomeByCustomer();

	}

//http://localhost:8080/income/allCompIncome
	@GetMapping(value = "/allCompIncome")
	public Collection<Income> vievIncomeByCompany() {
		return incomeService.vievIncomeByCompany();
	}

}
