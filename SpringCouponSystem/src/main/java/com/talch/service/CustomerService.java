package com.talch.service;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.talch.beans.Coupon;
import com.talch.beans.Customer;
import com.talch.repo.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> insertCustomer(Customer customer) {
		customerRepository.save(customer);
		return customerRepository.findAll();
	}

	public List<Customer> deleteCustomer(Long id) {
		customerRepository.deleteById(id);
		return findAll();
	}

	public String deleteCustomers() {
		customerRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer updateCustomer(Long id, Customer customer) {
		Customer custToUpdate = customerRepository.getOne(id);
		custToUpdate.setCustName(customer.getCustName());
		custToUpdate.setPassword(customer.getPassword());
		customerRepository.save(custToUpdate);
		return custToUpdate;

	}

	public void purchoiseCoupon(Long id, List<Coupon> coupons) {
		Customer compToUpdate = customerRepository.getOne(id);
		compToUpdate.setCupons(coupons);
		customerRepository.save(compToUpdate);

	}

}
