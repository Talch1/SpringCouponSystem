package com.talch.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.exeption.ExistEx;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;

import lombok.Data;

@Service
@Transactional
@Data
@RequiredArgsConstructor
public class AdminFacade implements Facade {


	private final long id = 1;

	private String name = "Admin";

	private Role role = Role.Admin;

	private final UserRepository userRepository;

	private final CouponRepository couponRepository;

	private final IncomeService incomeService;

	private final Utils utils;

	private final  ResponseEntity responseEntitySomesingWrong= ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body("Somesing Wrong");

	Date date = new Date(System.currentTimeMillis());
	Date dateMinusDayDate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
	Date dateMinusFiveDyes = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 5));
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
		coup.add(new Coupon(152, "3+1", dateMinusFiveDyes, dateMinusDayDate, 5, coupType, "wow!", 50,
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

	// **************************user************************************

	public ResponseEntity insertUser(User user) {
		List<User> users = userRepository.findAll();

		if ((users.stream().filter(u -> u.getId() == user.getId()).findFirst().isPresent())
				|| (users.stream().filter(u -> u.getUserName().equals(user.getUserName())).findFirst().isPresent())) {
			return  responseEntitySomesingWrong;
		}
			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(user.getId()));
	}

	public ResponseEntity deleteUserById(long userId, Role role) {
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()&& utils.checkRole(userId,role)){
			userRepository.deleteById(userId);
			List<User> allUsers = userRepository.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(allUsers.stream().
					filter(u -> u.getRole().equals(role)).collect(Collectors.toList()));
		} return responseEntitySomesingWrong;
	}

	public ResponseEntity<String> deleteAllUsers(Role role) {
		List<User> users = userRepository.findAll().stream().
				filter(user -> user.getRole().equals(role)).
				collect(Collectors.toList());
		userRepository.deleteAll(users);
		return ResponseEntity.status(HttpStatus.OK).
				body("All users was delete");
	}

	public ResponseEntity getUserById(long id,Role role) {
		if (utils.checkRole(id, role)) {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
		}
		return responseEntitySomesingWrong;
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public ResponseEntity findAllCust(){
		return  ResponseEntity.status(HttpStatus.OK)
				.body(userRepository.findAll().stream().
						filter(user -> user.getRole().equals(Role.Customer)).
						collect(Collectors.toList()));
	}
	
	public ResponseEntity findAllComp(){
		List<User> users = findAllUsers();
		List<User> compList = new ArrayList<User>();
		for (User user : users) {
			if (user.getRole().equals(Role.Company)) {
				compList.add(user);
			}
		}
		return ResponseEntity.status(HttpStatus.OK)userRepository.findAll().stream().filter(user -> user.getRole().equals(Role.Company));
	}
	
	public ResponseEntity<?> updateUser(long userIdtoUpdate, User user,Role role) {
		Optional<User> userToUpdate = userRepository.findById(userIdtoUpdate);
		if (utils.checkRole(userIdtoUpdate,role)) {
			userToUpdate.get().setUserName(user.getUserName());
			userToUpdate.get().setEmail(user.getEmail());
			userToUpdate.get().setPassword(user.getPassword());
			userRepository.save(userToUpdate.get());
			return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(userIdtoUpdate));
		}return responseEntitySomesingWrong;}

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

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
