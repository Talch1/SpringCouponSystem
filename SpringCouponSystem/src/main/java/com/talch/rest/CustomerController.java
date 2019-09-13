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
import org.springframework.web.bind.annotation.RestController;
import com.talch.beans.Coupon;
import com.talch.beans.Customer;
import com.talch.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	// http://localhost:8080/customer/custCreate
	@PostMapping(value = "/custCreate")
	public List<Customer> insertCu(@RequestBody Customer customer) {
		customerService.insertCustomer(customer);
		return customerService.findAll();
	}

	// http://localhost:8080/customer/getCustByID/{id}
	@GetMapping(value = "/getCustByID/{id}")
	Optional<Customer> findById(@PathVariable Long id) {
		return customerService.findById(id);
	}

	// http://localhost:8080/customer/getCustomers
	@GetMapping(value = "/getCustomers")
	public List<Customer> getAllCustomers() {
		return customerService.findAll();
	}

	// http://localhost:8080/customer/deleteCust/{id}
	@DeleteMapping(value = "/deleteCust/{id}")
	public List<Customer> deleteCustomer(@PathVariable Long id) {
		return customerService.deleteCustomer(id);

	}

	// http://localhost:8080/customer/deleteCust/All
	@DeleteMapping(value = "/deleteCust/All")
	public String deleteCustomers() {
		return customerService.deleteCustomers();

	}

	// http://localhost:8080/customer/updateCustomer
	@PutMapping(value = "/updateCustomer/{id}")
	public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		customerService.updateCustomer(id, customer);
		return customer;
	}

	// http://localhost:8080/customer/addCouponToCust
	@PutMapping(value = "/addCouponToCust/{id}")
	public String addCompany(@PathVariable Long id, @RequestBody List<Coupon> coupons) {
		customerService.purchoiseCoupon(id, coupons);
		return "Coupons Added";
	}

}