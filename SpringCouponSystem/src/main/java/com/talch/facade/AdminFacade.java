package com.talch.facade;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talch.CouponSystem;
import com.talch.beans.ClientType;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.Customer;
import com.talch.exeption.LogginEx;
import com.talch.service.AdminService;

import lombok.NoArgsConstructor;
import lombok.ToString;
@Component
@NoArgsConstructor
@ToString
public class AdminFacade implements CouponClientFacade {

	@Autowired
	AdminService adminService;

	public void createCompany(Company company) {
		adminService.insertCompany(company);
	}

	public void removeCompanys() {
		adminService.deleteCompanys();
	}

	public void removeCompany(Company company) {
		adminService.deleteCompanyfromCompany(company.getId());
	}

	public void updateCompany(Company company, long id) {
		adminService.updateCompany(id, company);
	}

	public Optional<Company> getCompany(long id) {

		return adminService.geCompany(id);
	}

	public Collection<Company> getAllCompanyes() {
		return adminService.findAllCom();
	}

	public Collection<Coupon> getAllcouponsByCompId(long id) {
		Optional<Company> company = adminService.geCompany(id);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		return coupons;

	}

	public void createCustomer(Customer cust) {
		adminService.insertCust(cust);

	}

	public void removeCustomer(Customer cust) {
		adminService.deleteCustomer(cust.getId());
		}
	
	public void removeAllCustomer() {
		adminService.deleteCustomers();
		}
	
		public void updateCustomer(Customer cust,long id)  {
			adminService.updateCustomer(id, cust);
		}

	public Optional<Customer> getCustomer(long id)  {
		return adminService.findById(id);
	}
		
		public Collection<Customer> getAllCustomers(){
		return adminService.findAllCust();
	}

		public CouponClientFacade login(String name, String password, ClientType c) throws LogginEx {
		if ((name.equals("Admin")) && (password.equals("1234"))) {
			AdminFacade adminFacade = new AdminFacade();
				return adminFacade;
			} else {
				throw new LogginEx("Invalid email or password");
		}

	}
}
