package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.talch.beans.ClientType;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Customer;
import com.talch.repo.CouponRepository;
import com.talch.repo.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	ClientType clientType = ClientType.Customer;

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CouponRepository couponRepository;
	
	

	public String getClientType() {
		return clientType.name();
	}

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

	public Customer updateCustomer(long id, Customer customer) {
		Customer custToUpdate = customerRepository.getOne(id);
		custToUpdate.setCustName(customer.getCustName());
		custToUpdate.setPassword(customer.getPassword());
		customerRepository.save(custToUpdate);
		return custToUpdate;

	}

	public void purchouseCoupon(long id, long coupId) {
	
		Customer custToUpdate = customerRepository.getOne(id);
		Coupon coupon = couponRepository.getOne(coupId);
		List<Coupon> coupons =  (List<Coupon>) custToUpdate.getCupons();
		coupons.add(coupon);
		custToUpdate.setCupons(coupons);
		customerRepository.save(custToUpdate);
;

	}

	public List<Coupon> getCouponByType(CouponType type, long compId) {
		Optional<Customer> company = customerRepository.findById(compId);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		List<Coupon> byType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getType().equals(type)) {
				byType.add(coupon);
			}
		}
		return byType;

	}

	public List<Coupon> getCouponByDate(Date date, long compId) {
		Optional<Customer> company = customerRepository.findById(compId);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		List<Coupon> byDate = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(date)) {
				byDate.add(coupon);
			}
		}
		return byDate;

	}

	public List<Coupon> getCouponWhenPriceBetwenPrice(Double price1, long custId) {
		Optional<Customer> company = customerRepository.findById(custId);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		List<Coupon> byPrice = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice()<price1) {
				byPrice.add(coupon);
			}
		}
		return byPrice;

	}

	public boolean loggin(String custName, String password, String clientType) {

		if (customerRepository.existsById(customerRepository.findByCustNameAndPassword(custName, password).getId())
				&& clientType.equals(ClientType.Customer.toString())) {
			return true;
		} else {
			return false;
		}

	}

	public List<Coupon> findAllCoup(long custId) {
			Optional<Customer> company	= customerRepository.findById(custId);
			List<Coupon> coupons= (List<Coupon>) company.get().getCupons();
			return coupons;
		}

	public Coupon getCouponById(long custId, long coupId) {
		Customer customer = customerRepository.getOne(custId);
		List<Coupon> coupons = (List<Coupon>) customer.getCupons();
		Coupon coupon1 = new Coupon();
		for (Coupon coupon : coupons) {
			if (coupon.getId()== coupId) {
				coupon1 = coupon;
			}
		
		}
		return coupon1;
	}
	

}
