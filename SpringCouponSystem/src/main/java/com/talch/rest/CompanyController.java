package com.talch.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.Company;
import com.talch.service.CompanyService;

@RestController
@RequestMapping("company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	// http://localhost:8080/company/compCreate
	@PostMapping(value = "/compCreate")
	public List<Company> insertC(@RequestBody Company company) {
		companyService.insertComp(company);
		return companyService.findAll();

	}

	// http://localhost:8080/company/getByID/{id}
	@GetMapping(value = "/getCompByID/{id}")
	Optional<Company> findById(@PathVariable Long id) {
		return companyService.findById(id);
	}

	// http://localhost:8080/company/getCompanys
	@GetMapping(value = "/getCompanys")
	public List<Company> getAllCompanys() {
		return companyService.findAll();
	}

	// http://localhost:8080/company/delete/{id}
	@DeleteMapping(value = "/deleteComp/{id}")
	public List<Company> deleteCompany(@PathVariable Long id) {
		return companyService.deleteCompanyfromCompany(id);

	}

	// http://localhost:8080/company/delete/All
	@DeleteMapping(value = "/deleteComp/All")
	public String deleteCompany() {
		companyService.deleteCompanys();
		return "All Companyes deleted";
	}

	// http://localhost:8080/company/compUpadte
	@PutMapping(value = "/compUpdate")
	public Company updateCompany(@RequestParam Long id, @RequestBody Company company) {
		companyService.updateCompany(id, company);
		return company;
	}

}
