package com.talch.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.talch.rest.CustomSession;
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

	public ResponseEntity insertUser(User user, String token) {
	CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if(utils.checkRole(session,Role.Admin)) {
			List<User> users = userRepository.findAll();

			if (user.getRole().equals(Role.Customer)) {

				user.setRole(Role.Customer);
				user.setAmount(1000);
				user.setEmail(null);
			} else if ((user.getRole().equals(Role.Company))) {
				user.setRole(Role.Company);
				user.setAmount(100000);
			}

			if ((users.stream().filter(u -> u.getId() == user.getId()).findFirst().isPresent())
					|| (users.stream().filter(u -> u.getUserName().equals(user.getUserName())).findFirst().isPresent())) {
				return utils.getResponseEntitySomesingWrong();
			}
			userRepository.save(user);

			return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(user.getId()));
		}return  utils.getResponseEntitySesionNull();
	}

	public ResponseEntity deleteUserById(long userId,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()&& utils.checkRole(session,Role.Admin)){
			userRepository.deleteById(userId);
			List<User> allUsers = userRepository.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(allUsers.stream().
					filter(u -> u.getRole().equals(role)).collect(Collectors.toList()));
		} return utils.getResponseEntitySomesingWrong();
	}

	public ResponseEntity<String> deleteAllUsers(String token,Role role) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			List<User> users = userRepository.findAll().stream().
					filter(user -> user.getRole().equals(role)).
					collect(Collectors.toList());
			userRepository.deleteAll(users);
			return ResponseEntity.status(HttpStatus.OK).
					body("All users was delete");
		}return utils.getResponseEntitySesionNull();
	}

	public ResponseEntity getUserById(long id,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<User> user = userRepository.findById(id);
		if (utils.checkRole(session, Role.Admin)&&(user.isPresent())) {
				return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return utils.getResponseEntitySomesingWrong();
	}

	public ResponseEntity findAllCust(String token){
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userRepository.findAll().stream().
							filter(user -> user.getRole().equals(Role.Customer)).
							collect(Collectors.toList()));
		}return utils.getResponseEntitySomesingWrong();
	}
	
	public ResponseEntity findAllComp(String token){
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userRepository.findAll().stream().
							filter(user -> user.getRole().equals(Role.Company)).
							collect(Collectors.toList()));
		}return utils.getResponseEntitySomesingWrong();
	}
	
	public ResponseEntity<?> updateUser(long userIdtoUpdate, User user,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<User> userToUpdate = userRepository.findById(userIdtoUpdate);
		if (utils.checkRole(session,Role.Admin) && userToUpdate.isPresent()) {
			userToUpdate.get().setUserName(user.getUserName());
			userToUpdate.get().setEmail(user.getEmail());
			userToUpdate.get().setPassword(user.getPassword());
			userRepository.save(userToUpdate.get());
			return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(userIdtoUpdate));
		}return utils.getResponseEntitySomesingWrong();}

	public ResponseEntity getUserByNameAndPass(String name, String pass,Role role,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<User> user = userRepository.findByUserNameAndPassword(name, pass);
		if (utils.checkRole(session,Role.Admin)&&(user.get().getRole().equals(role))){
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		}
		return utils.getResponseEntitySomesingWrong() ;
	}

	public ResponseEntity addCouponToUser(long userId, long coupId,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<Coupon> coupon = couponRepository.findById(coupId);
		Optional<User> userToUpdate = userRepository.findById(userId);
		if (coupon.isPresent() && userToUpdate.isPresent() &&
				((utils.checkRole(session,Role.Admin)) )){
			List<Coupon> coupons = (List<Coupon>) userToUpdate.get().getCupons();
			coupons.add(coupon.get());
			return ResponseEntity.status(HttpStatus.OK).body(coupons);
		}
		return utils.getResponseEntitySomesingWrong();
	}

	// **************************CouponS************************************

	public ResponseEntity addCoupon(Coupon coupon,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if ((!userRepository.findById(coupon.getId()).isPresent())&& (utils.checkRole(session,Role.Admin))) {
			couponRepository.save(coupon);
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
		}
		return utils.getResponseEntitySomesingWrong();
	}

	public ResponseEntity getAllcouponsByUserId(long id,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			Collection<Coupon> coupons = userRepository.findById(id).get().getCupons();
			return ResponseEntity.status(HttpStatus.OK).body(coupons);
		}return utils.getResponseEntitySesionNull();
	}

	public ResponseEntity deleteCoupon(long coupId,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			couponRepository.deleteById(coupId);
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
		} return utils.getResponseEntitySesionNull();
	}

	public ResponseEntity updateCouponAdmin(Coupon coupon,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<Coupon> coupToUpdate = couponRepository.findById(coupon.getId());
		if (coupToUpdate.isPresent() && utils.checkRole(session,Role.Admin)){
			coupToUpdate.get().setEndDate(coupon.getEndDate());
			coupToUpdate.get().setPrice(coupon.getPrice());
			couponRepository.save(coupToUpdate.get());
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findById(coupon.getId()).get());
		} return  utils.getResponseEntitySomesingWrong();
	}

	public ResponseEntity<String> deleteCoupons(String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			couponRepository.deleteAll();
			return ResponseEntity.status(HttpStatus.OK).body("All Coupons Deleted");
		}return  utils.getResponseEntitySesionNull();
	}

	public ResponseEntity findCoupById(long id,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		Optional<Coupon> coupon = couponRepository.findById(id);
		if (coupon.isPresent()&&(utils.checkRole(session,Role.Admin))) {
			return ResponseEntity.status(HttpStatus.OK).body(coupon);
		}
		return  utils.getResponseEntitySomesingWrong();
	}

	public ResponseEntity findAllCoup(String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if (utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
		}
		return  utils.getResponseEntitySesionNull();
	}

	public ResponseEntity getCouponByType(CouponType type,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if ( utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByType(type));
		}
		return utils.getResponseEntitySesionNull();
	}

	public ResponseEntity getCouponByDate(Date date,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if ( utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByEndDateBefore(date));
		}
		return utils.getResponseEntitySesionNull();
	}

	public ResponseEntity getCouponWhenPriceBetwenPrice(Double price1,String token) {
		CustomSession session = utils.isActive(token);
		session.setLastAccessed(System.currentTimeMillis());
		if ( utils.checkRole(session,Role.Admin)) {
			return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByPriceLessThan(price1));
		}
		return utils.getResponseEntitySesionNull();
	}
}
