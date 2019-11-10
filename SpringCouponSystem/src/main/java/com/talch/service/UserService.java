package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.hibernate.annotations.Check;
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


	public void addCouponToUser(long userId, long coupId) throws ExistEx {
		Coupon coupon = couponRepository.getOne(coupId);
		User userToUpdate = userRepository.getOne(userId);
		List<Coupon> coupons = (List<Coupon>) userToUpdate.getCupons();
		if (coupons.contains(coupon)) {
			throw new ExistEx("You have this coupon");
		}
		coupons.add(coupon);
		userRepository.save(userToUpdate);

	}

	public Collection<Coupon> getAllcouponsByUserId(long id) {
		Optional<User> user = userRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		return coupons;

	}

	public Coupon getCouponByCustId(long custId, long coupId) throws ExistEx {
		Optional<User> user = userRepository.findById(custId);
		Coupon userCoupon = new Coupon();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId() == coupId) {
				userCoupon = coupon;
			} else {
				throw new ExistEx("This coupon not exist");
			}
		}
		return userCoupon;

	}

	public List<Coupon> getUserCouponByType(long userId, CouponType type) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getType() != type) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getUserCouponByDateBefore(long userId, Date date) {
		Optional<User> user = userRepository.findById(userId);
		List<Coupon> userCoupons = new ArrayList<Coupon>();
		List<Coupon> coupons = (List<Coupon>) user.get().getCupons();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(date)) {
				userCoupons.add(coupon);
			}
		}
		return userCoupons;
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

	public boolean loggin(String userName, String password, String role) {
		if ((userName.equals("admin") && password.equals("1234") && role.equals(Role.Admin.name()))
				|| (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
						&& (userRepository.findByUserNameAndPassword(userName, password).getRole())
								.equals(Role.Customer))
				|| (userRepository.existsById(userRepository.findByUserNameAndPassword(userName, password).getId())
						&& (userRepository.findByUserNameAndPassword(userName, password).getRole())
								.equals(Role.Company))) {
			return true;

		}
		return false;
	}

}