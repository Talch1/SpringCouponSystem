package com.talch.facade;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talch.beans.ClientType;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Customer;
import com.talch.exeption.CouponExistEx;
import com.talch.exeption.LogginEx;
import com.talch.service.CustomerService;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@NoArgsConstructor
@ToString
public class CustomerFacade implements CouponClientFacade {
	@Autowired
	CustomerService customerService;

	public void buyCoupon(Coupon coupon, Customer customer) throws CouponExistEx {
		boolean checkIfExist = false;

		Optional<Customer> customer2 = customerService.findById(customer.getId());

		List<Coupon> coupons = (List<Coupon>) customer2.get().getCupons();

		for (Coupon coupon2 : coupons) {
			if (coupon.getTitle().equals(coupon2.getTitle())) {
				checkIfExist = true;
			}
		}
		if (coupon.getAmount() < 1) {
			throw new CouponExistEx("No more coupons for you!!");
		}
		if (checkIfExist == true) {
			throw new CouponExistEx("You have this coupon");
		}
		coupons.add(coupon);
		customer2.get().setCupons(coupons);
		customerService.updateCustomer(customer2.get().getId(), customer);
		System.out.println("Coupon Bought");
	}

	public List<Coupon> getAllPurchasedCoupons(Customer customer) {
		Optional<Customer> customer2 = customerService.findById(customer.getId());
		List<Coupon> coupons = (List<Coupon>) customer2.get().getCupons();
		return coupons;
	}

	public List<Coupon> getAllPurchisedCouponsByType(CouponType type, Customer customer) {
		Optional<Customer> customer2 = customerService.findById(customer.getId());
		List<Coupon> coupons = (List<Coupon>) customer2.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getType().equals(type)) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getAllPurchisedCouponsBetwenPrice(double price, Customer customer) {

		Optional<Customer> customer2 = customerService.findById(customer.getId());
		List<Coupon> coupons = (List<Coupon>) customer2.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < price) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}

	public CouponClientFacade login(String name, String password, ClientType c) throws LogginEx {

		if (customerService.loggin(name, password, c.name())) {

			CustomerFacade customerFacade = new CustomerFacade();
			return customerFacade;
		} else {
			throw new LogginEx("Invalid email or password");

		}

	}
}
