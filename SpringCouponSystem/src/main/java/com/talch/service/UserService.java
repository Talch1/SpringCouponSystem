package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;

	Date date = new Date(System.currentTimeMillis());
	Date datePlus5Days = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 5));
	Date datePlus1Min = new Date(System.currentTimeMillis() + (1000 * 60));

	CouponType coupType = CouponType.SPORTS;
	CouponType coupType2 = CouponType.HEALTH;
	CouponType coupType3 = CouponType.RESTURANS;

	

	@PostConstruct
	public void initDBCoup() {

		userRepository.deleteAll();
		couponRepository.deleteAll();

		List<Coupon> coup = new ArrayList<>();

		coup.add(new Coupon(1582, "1+1", date, datePlus5Days, 5, coupType, "just now!", 50.3,
				"www.gvdjshbs.com/xjh.gif"));
		coup.add(new Coupon(12, "2+1", date, datePlus5Days, 5, coupType2, "just today!", 100.7,
				"www.wcdvsjv.com/xjh.gif"));
		coup.add(new Coupon(82, "second helf price", date, datePlus1Min, 5, coupType3, "Sale!", 25.6,
				"www.gvdjsjznalnhbs.com/xjh.gif"));

		couponRepository.saveAll(coup);

		List<User> users = new ArrayList<>();
		users.add(new User(158, Role.Company, "Kia", "kiamotors", "kiamotors@kiamotors.net", null));
		users.add(new User(120, Role.Company, "Cola", "cocacola", "Cola@cola.net", null));
		users.add(new User(201, Role.Company, "Osem", "bisli", "osem@osem.com", null));

		users.add(new User(2010, Role.Customer, "Gabi", "12345", null, null));
		users.add(new User(15518, Role.Customer, "Tomer", "good", null, null));

		userRepository.saveAll(users);
	}
//**************************user************************************

	public List<User> insertUser(User user) {
		userRepository.save(user);
		return userRepository.findAll();
	}

	public List<User> deleteUserById(Long id) {
		userRepository.deleteById(id);
		return userRepository.findAll();

	}

	public String deleteAllUsers(Role role)  {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (!user.getRole().equals(role)) {
				users.remove(user);
			}
		}
	
		userRepository.deleteAll(users);
		return "All Customers Deleted ";
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User updateUser(long userIdtoUpdate, User user) {
		User userToUpdate = userRepository.getOne(userIdtoUpdate);
		userToUpdate.setUserName(user.getUserName());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setPassword(user.getPassword());
		userRepository.save(userToUpdate);
		return userToUpdate;

	}

	public User getUserByNameAndPass(String name, String pass) {
		return userRepository.findByUserNameAndPassword(name, pass);
	}

	public void addCoupons(long id, List<Coupon> coupons) {
		User userToUpdate = userRepository.getOne(id);
		userToUpdate.setCupons(coupons);
		userRepository.save(userToUpdate);

	}

	public Collection<Coupon> getAllcouponsByCompId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}
	
		public void addCouponToUser(long userId, long coupId) {
			Coupon coupon = couponRepository.getOne(coupId);
		User userToUpdate = userRepository.getOne(userId);
		List<Coupon> coupons = (List<Coupon>) userToUpdate.getCupons();
		coupons.add(coupon);
		userRepository.save(userToUpdate);

	}

	public Collection<Coupon> getAllcouponsByUserId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}
	
	public Coupon getCouponByCustId(long custId,long coupId) throws ExistEx {
		Optional<User> user = userRepository.findById(custId);
		Coupon userCoupon = new Coupon();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId()==coupId) {
				userCoupon = coupon;
			}else {
				throw new ExistEx("This coupon not exist");
			}
		}
		return userCoupon;
	
	}
	public List<Coupon> getCustCouponByType(long custId,CouponType type) {
		Optional<User> user = userRepository.findById(custId);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getType()!=type) {
				coupons.remove(coupon);
			}
	}
		return coupons;
	}
	public List<Coupon> getCustCouponByDateBefore(long custId,Date date) {
		Optional<User> user = userRepository.findById(custId);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(date)) {
				coupons.remove(coupon);
			}
	}
		return coupons;
	}
	
	public List<Coupon> getCustCouponByPriceLessThat(long custId, double price) {
		Optional<User> user = userRepository.findById(custId);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice()> price) {
				coupons.remove(coupon);
			}
	}
		return coupons;
	}
	
	
	// **************************CouponS************************************

	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}
	

	public List<Coupon> deleteCoupon(long id) {
		couponRepository.deleteById(id);
		return couponRepository.findAll();
	}

	public Coupon updateCoupon(Coupon coupon) {
		Coupon coupToUpdate = couponRepository.getOne(coupon.getId());
		coupToUpdate.setEndDate(coupon.getEndDate());

		coupToUpdate.setPrice(coupon.getPrice());
		couponRepository.save(coupToUpdate);

		return coupToUpdate;

	}

	public String deleteCoupons() {
		couponRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Coupon> findCoupById(Long id) {
		return couponRepository.findById(id);
	}

	public List<Coupon> findAllCoup() {
		return couponRepository.findAll();
	}

	public List<Coupon> getCouponByType(CouponType type) {
		return couponRepository.findByType(type);
	}

	public List<Coupon> getCouponByDate(Date date) {

		return couponRepository.findByEndDateBefore(date);

	}

	public List<Coupon> getCouponWhenPriceBetwenPrice(Double price1) {
		return couponRepository.findByPriceLessThan(price1);

	}

	public boolean loggin(String userName, String password, String role) {
		if ((userName.equals("admin") && password.equals("1234") && role.equals(Role.Admin.name())) || (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
				&& (userRepository.findByUserNameAndPassword(userName, password).getRole()).equals(Role.Customer)) || (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
						&& (userRepository.findByUserNameAndPassword(userName, password).getRole()).equals(Role.Company))  ) {
			return true;
				
		}
		return false;
	}



}