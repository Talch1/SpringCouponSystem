package com.talch.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Income;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;

@Service
@Transactional
public class AdminFacade implements Facade{
	
	
	private long id = 1;
	private String name= "Admin";
	private Role role = Role.Admin;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	IncomeService incomeService;

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

		coup.add(new Coupon(1582, "1+1", date, datePlus5Days, 5, coupType, "just now!", 50,
				"https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));
		coup.add(new Coupon(12, "2+1", date, datePlus5Days, 5, coupType2, "just today!", 82,
				"https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));
		coup.add(new Coupon(82, "second helf price", date, datePlus1Min, 5, coupType3, "Sale!", 25,
				"https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));

		couponRepository.saveAll(coup);

		List<User> users = new ArrayList<>();
		users.add(new User(201, Role.Company, "Kia", "kiamotors", "kiamotors@kiamotors.net", 1000000, null));
		users.add(new User(521, Role.Company, "Cola", "cocacola", "Cola@cola.net", 1000000, null));
		users.add(new User(17, Role.Company, "Osem", "bisli", "osem@osem.com", 1000000, null));

		users.add(new User(2010, Role.Customer, "Gabi", "151285", null, 1000, null));
		users.add(new User(20, Role.Customer, "Tomer", "goodday", null, 1500, null));
		users.add(new User(30, Role.Customer, "Igor", "987456321", null, 1200, null));

		userRepository.saveAll(users);
	}

	public Collection<Income> viewAllIncomes() {
		return incomeService.viewAllIncome();

	}

	private Collection<Income> viewAllCustIncomes() {
		return incomeService.vievIncomeByCustomer();
	}
	// **************************user************************************

	public Optional<User> insertUser(User user) throws ExistEx {
		boolean check = false;
		List<User> users = userRepository.findAll();
		for (User user2 : users) {
			if (user.getId() == user2.getId() || user.getUserName().equals(user2.getUserName())) {
				check = true;
			}
		}
		if (check) {
			throw new ExistEx("this id or name is exist");
		} else {
			userRepository.save(user);
			return userRepository.findById(user.getId());
		}
	}

	public List<User> deleteUserById(long userId, Role role) {

		userRepository.deleteById(userId);
		List<User> allUsers = userRepository.findAll();
		List<User> sorted = new ArrayList<User>();
		for (User user : allUsers) {
			if (user.getRole().equals(role)) {
				sorted.add(user);
			}
		}
		return sorted;
	}

	public String deleteAllUsers(Role role) {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (!user.getRole().equals(role)) {
				users.remove(user);
			}
		}

		userRepository.deleteAll(users);
		return "All Customers Deleted ";
	}

	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public List<User> findAllCust(){
		List<User> users = findAllUsers();
		List<User> customersList = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().equals(Role.Customer)) {
				customersList.add(user);
			}
		}
		return customersList;
	}
	
	public List<User> findAllComp(){
		List<User> users = findAllUsers();
		List<User> compList = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().equals(Role.Company)) {
				compList.add(user);
			}
		}
		return compList;
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

	// **************************CouponS************************************

	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}

	public Collection<Coupon> deleteCouponByUser(long userId, long coupId) {

		User user = userRepository.getOne(userId);
		List<Coupon> list = (List<Coupon>) user.getCupons();
		List<Coupon> list2 = new ArrayList<Coupon>();
		for (Coupon coupon : list) {
			if (coupon.getId() != coupId) {
				list2.add(coupon);
			}
		}
		user.setCupons(list2);
		userRepository.save(user);
		return list2;

	}

	public List<Coupon> deleteCoupon(long coupId) {
		couponRepository.deleteById(coupId);
		return couponRepository.findAll();

	}

	public List<Coupon> findAllCouponsByUser(long userId) {
		return (List<Coupon>) userRepository.getOne(userId).getCupons();
	}

	public Coupon findAllCoupByUser(long userId, long coupId) {
		List<Coupon> coupons = (List<Coupon>) userRepository.getOne(userId).getCupons();
		Coupon coupon2 = new Coupon();
		for (Coupon coupon : coupons) {
			if (coupId == coupon.getId()) {
				coupon2 = couponRepository.getOne(coupId);
			}
		}
		return coupon2;
	}

	public Coupon updateCouponAdmin(Coupon coupon) {

		Coupon coupToUpdate = couponRepository.getOne(coupon.getId());
		coupToUpdate.setEndDate(coupon.getEndDate());

		coupToUpdate.setPrice(coupon.getPrice());
		couponRepository.save(coupToUpdate);

		return couponRepository.getOne(coupon.getId());

	}

	public Coupon updateCoupon(Coupon coupon, long userId) throws ExistEx {
		boolean check = false;
		List<Coupon> list = (List<Coupon>) userRepository.getOne(userId).getCupons();
		for (Coupon coupon2 : list) {
			if (coupon.getId() == coupon2.getId()) {
				check = true;
			}
		}
		if (check) {

			Coupon coupToUpdate = couponRepository.getOne(coupon.getId());
			coupToUpdate.setEndDate(coupon.getEndDate());

			coupToUpdate.setPrice(coupon.getPrice());
			couponRepository.save(coupToUpdate);
		} else {
			throw new ExistEx("You don't have this coupon");
		}
		return couponRepository.getOne(coupon.getId());

	}

	public List<Coupon> deleteCouponsByUser(long userId) {
		Optional<User> user = userRepository.findById(userId);
		user.get().setCupons(null);
		return (List<Coupon>) user.get().getCupons();
	}

	public String deleteCoupons() {
		couponRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Coupon> findCoupById(long id) {
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

}
