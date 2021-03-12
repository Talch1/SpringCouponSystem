package com.talch.facade;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;
import com.talch.service.IncomeService;
import com.talch.utils.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
@RequiredArgsConstructor
public class AdminFacade implements Facade {


    private final long id = 1;

    private final String name = "Admin";

    private final Role role = Role.Admin;

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    private final IncomeService incomeService;

    private final Utils utils;



    // **************************user************************************
    public ResponseEntity insertUser(User user, String token) {
        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
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
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity deleteUserById(long userId, String token) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && utils.checkRole(utils.isActive(token), Role.Admin)) {
            userRepository.deleteById(userId);
            List<User> allUsers = userRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(allUsers.stream().
                    filter(u -> u.getRole().equals(role)).collect(Collectors.toList()));
        }
        return utils.getResponseEntitySomesingWrong();
    }


    public ResponseEntity<String> deleteAllUsers(String token, Role role) {
        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            List<User> users = userRepository.findAll().stream().
                    filter(user -> user.getRole().equals(role)).
                    collect(Collectors.toList());
            userRepository.deleteAll(users);
            return ResponseEntity.status(HttpStatus.OK).
                    body("All users was delete");
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserById(long id, String token) {
        Optional<User> user = userRepository.findById(id);
        if (utils.checkRole(utils.isActive(token), Role.Admin) && (user.isPresent())) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity findAllCust(String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userRepository.findAll().stream().
                            filter(user -> user.getRole().equals(Role.Customer)).
                            collect(Collectors.toList()));
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity findAllComp(String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userRepository.findAll().stream().
                            filter(user -> user.getRole().equals(Role.Company)).
                            collect(Collectors.toList()));
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity<?> updateUser(long userIdtoUpdate, User user, String token) {

        Optional<User> userToUpdate = userRepository.findById(userIdtoUpdate);
        if (utils.checkRole(utils.isActive(token), Role.Admin) && userToUpdate.isPresent()) {
            userToUpdate.get().setUserName(user.getUserName());
            userToUpdate.get().setEmail(user.getEmail());
            userToUpdate.get().setPassword(user.getPassword());
            userRepository.save(userToUpdate.get());
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(userIdtoUpdate));
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity getUserByNameAndPass(String name, String pass, Role role, String token) {

        Optional<User> user = userRepository.findByUserNameAndPassword(name, pass);
        if (utils.checkRole(utils.isActive(token), Role.Admin) && (user.get().getRole().equals(role))) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity addCouponToUser(long userId, long coupId, String token) {

        Optional<Coupon> coupon = couponRepository.findById(coupId);
        Optional<User> userToUpdate = userRepository.findById(userId);
        Collection <Coupon> userCoupon = userToUpdate.get().getCupons();

        if (utils.checkRole(utils.isActive(token), Role.Admin)||userCoupon.contains(coupon.get())){
            return utils.getResponseEntitySomesingWrong();
        }
        List<Coupon> coupons = (List<Coupon>) userToUpdate.get().getCupons();
        coupons.add(coupon.get());
        System.out.println(utils.checkRole(utils.isActive(token), Role.Admin));
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    // **************************CouponS************************************

    public ResponseEntity addCoupon(Coupon coupon, String token) {

        if ((!userRepository.findById(coupon.getId()).isPresent()) &&
                (utils.checkRole(utils.isActive(token), Role.Admin))) {
            couponRepository.save(coupon);
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity getAllcouponsByUserId(long id, String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            Collection<Coupon> coupons = userRepository.findById(id).get().getCupons();
            return ResponseEntity.status(HttpStatus.OK).body(coupons);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity deleteCoupon(long coupId, String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            couponRepository.deleteById(coupId);
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity updateCouponAdmin(Coupon coupon, String token) {

        Optional<Coupon> coupToUpdate = couponRepository.findById(coupon.getId());
        if (coupToUpdate.isPresent() && utils.checkRole(utils.isActive(token), Role.Admin)) {
            coupToUpdate.get().setEndDate(coupon.getEndDate());
            coupToUpdate.get().setPrice(coupon.getPrice());
            couponRepository.save(coupToUpdate.get());
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findById(coupon.getId()).get());
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity<String> deleteCoupons(String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            couponRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("All Coupons Deleted");
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity findCoupById(long id, String token) {

        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isPresent() && (utils.checkRole(utils.isActive(token), Role.Admin))) {
            return ResponseEntity.status(HttpStatus.OK).body(coupon);
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity findAllCoup(String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getCouponByType(CouponType type, String token) {

        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByType(type));
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getCouponByDate(Date date, String token) {
        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByEndDateBefore(date));
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getCouponWhenPriceBetwenPrice(Double price1, String token) {
        if (utils.checkRole(utils.isActive(token), Role.Admin)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findByPriceLessThan(price1));
        }
        return utils.getResponseEntitySesionNull();
    }
}
