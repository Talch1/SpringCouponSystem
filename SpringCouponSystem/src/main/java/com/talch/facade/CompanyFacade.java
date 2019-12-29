package com.talch.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Coupon;
import com.talch.beans.Description;
import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;

import lombok.Data;

@Service
@Transactional
@Data
public class CompanyFacade implements Facade {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	IncomeService incomeService;

	private long compId;
	private String compName;
	private Role role = Role.Company;

	public void addCouponToUser(long userId, long coupId) {
		if ((couponRepository.getOne(coupId) != null) && (userRepository.getOne(userId) != null)) {
			Coupon coupon = couponRepository.getOne(coupId);
			User userToUpdate = userRepository.getOne(userId);
			List<Coupon> coupons = (List<Coupon>) userToUpdate.getCupons();
			coupons.add(coupon);
		}
	}

	public Optional<Coupon> findCoupById(long id) {
			boolean check = false;
			Collection<Coupon> userCoupons = userRepository.getOne(this.compId).getCupons();
			for (Coupon coupon2 : userCoupons) {
				if (id == coupon2.getId()) {
					check = true;
				}
			}
			if (check) {
				return couponRepository.findById(id);
			}
			return null;
		

	}

	public List<Coupon> addCoupon(Coupon coupon, long compId) throws ExistEx {
		if (couponRepository.findById(coupon.getId()).isPresent()){	
			throw new ExistEx("This coupon is exist");
	}else {
		couponRepository.save(coupon);
		Income income = new Income();
		User userToUpdate = userRepository.getOne(compId);

		income.setName(userToUpdate.getUserName());
		income.setDate(new java.util.Date(System.currentTimeMillis()));
		income.setDescription(Description.Company_Add_Coupon);
		income.setAmount(100);
		income.setRole(userToUpdate.getRole());
		income.setUserId(compId);
		incomeService.storeIncome(income);

		userToUpdate.setAmount(userToUpdate.getAmount() - 100);
		List<Coupon> coupons = (List<Coupon>) userToUpdate.getCupons();
		coupons.add(coupon);
		userToUpdate.setCupons(coupons);
		userRepository.save(userToUpdate);

		return couponRepository.findAll();	
	}
	}

	public List<Coupon> findAllCouponsByUser(long userId) {
		return (List<Coupon>) userRepository.getOne(userId).getCupons();
	}

	public Collection<Coupon> deleteCouponByUser(long userId, long coupId) throws ExistEx {

		User user = userRepository.getOne(userId);
		List<Coupon> list = (List<Coupon>) user.getCupons();
		List<Coupon> list2 = new ArrayList<Coupon>();
		if (list.contains(couponRepository.getOne(coupId))) {
			for (Coupon coupon : list) {
				if (coupon.getId() != coupId) {
					list2.add(coupon);
				}
			}
			user.setCupons(list2);
			userRepository.save(user);
			return list2;
		} else {
			throw new ExistEx("You don't have this coupon");
		}

	}

	public Collection<Coupon> getAllcouponsByUserId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}

	public List<Coupon> deleteCouponsByUser(long userId) {
		Optional<User> user = userRepository.findById(userId);
		user.get().setCupons(null);
		return (List<Coupon>) user.get().getCupons();
	}

	public Coupon updateCoupon(Coupon coupon, long userId) {
		boolean check = false;
		Collection<Coupon> userCoupons = userRepository.getOne(userId).getCupons();
		for (Coupon coupon2 : userCoupons) {
			if (coupon.getId() == coupon2.getId()) {
				check = true;
			}
		}
		if (check) {

	
		Coupon coupToUpdate = couponRepository.getOne(coupon.getId());
		coupToUpdate.setEndDate(coupon.getEndDate());
		coupToUpdate.setPrice(coupon.getPrice());

		Income income = new Income();
		income.setName(userRepository.getOne(userId).getUserName());
		income.setDate(new java.util.Date(System.currentTimeMillis()));
		income.setDescription(Description.Company_update_Coupon);
		income.setAmount(10);
		income.setUserId(userId);
		income.setRole(userRepository.getOne(userId).getRole());
		incomeService.storeIncome(income);

		User userToUpdate = userRepository.getOne(userId);
		userToUpdate.setAmount(userToUpdate.getAmount() - 10);
		userRepository.save(userToUpdate);

		couponRepository.save(coupToUpdate);

		return couponRepository.getOne(coupon.getId());
		}else {
			return null;
		}
	}

	public List<Coupon> getUserCouponByType(long userId, String type) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> allcoupons = (List<Coupon>) user.get().getCupons();
		List<Coupon>  sorted = new ArrayList<Coupon>();
		for (Coupon coupon : allcoupons) {
			if (coupon.getType().name().equals(type)) {
				sorted.add(coupon);
			}
		}
		return sorted;
	}

	public List<Coupon> getUserCouponByDateBefore(long userId, Date date) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> sorted = new ArrayList<Coupon>();
		List<Coupon> allCoupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : allCoupons) {
			if (coupon.getEndDate().before(date)) {
				sorted.add(coupon);
			}
		}
		return sorted;
	}

	public List<Coupon> getUserCouponByPriceLessThat(long userId, double price) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> userCoupons = new ArrayList<Coupon>();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < price) {
				userCoupons.add(coupon);
			}
		}
		return userCoupons;
	}

	public Coupon findCoupfromUserbyCouponId(long userId, long coupId) {
		List<Coupon> coupons = (List<Coupon>) userRepository.getOne(userId).getCupons();
		Coupon coupon2 = new Coupon();
		for (Coupon coupon : coupons) {
			if (coupId == coupon.getId()) {
				coupon2 = couponRepository.getOne(coupId);
			}
		}
		return coupon2;
	}

	public Collection<Income> viewIncomes() {
		return incomeService.vievIncomeByCompany(compId);
	}
	
	public List<Coupon> getAllCouponsOfAllCompanys() {
		return couponRepository.findAll();
	}

}
