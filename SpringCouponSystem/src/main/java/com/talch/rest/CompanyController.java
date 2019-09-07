package com.talch.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.Company;
import com.talch.service.CompanyService;

@RestController
@RequestMapping("company/")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
//	
//	// http://localhost:8080/company/addCompany/{company}
//	@PostMapping("/add/{company}")
//	public Company addCompany(@PathVariable Company company) {
//		return companyService.addCompanyToCompany(company);
//	}
//		
	
	
	// http://localhost:8080/company/getCompanys
		@GetMapping("/getCompanys")
		public List<Company> getAllCompanys() {
			return companyService.findAll();
		}
		
		// http://localhost:8080/company/delete/{id}
		@DeleteMapping("/delete/{id}")
		public List<Company> deleteCompany(@PathVariable Long id) {
			return companyService.deleteCompanyfromCompany(id);
			
		}
//
//		public Company updateCompany(Long id, Company company) {
//			Company compToUpdate = companyRepostory.getOne(id);
//			compToUpdate.setCompName(company.getCompName());
//			compToUpdate.setEmail(company.getEmail());
//			compToUpdate.setPassword(company.getPassword());
//			companyRepostory.save(compToUpdate);
//
//			return compToUpdate;
//
//		}
		// http://localhost:8080/company/getByID/{id}
		@GetMapping("/getByID/{id}")
		Optional<Company> findById(@PathVariable Long id) {
			return companyService.findById(id);
		}
	

}
