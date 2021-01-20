package com.talch.facade;


import com.talch.beans.*;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;
import com.talch.service.IncomeService;
import com.talch.utils.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
@RequiredArgsConstructor
public class CompanyFacade implements Facade {

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    private final IncomeService incomeService;

    private final Utils utils;

    private long id;
    private String name;
    private Role role = Role.Company;

    public ResponseEntity addCouponToUser(long coupId, String token) {
        Optional<Coupon> coupon = couponRepository.findById(coupId);
        Optional<User> userToUpdate = userRepository.findById(utils.isActive(token).getFacade().getId());
        if (utils.checkRole(utils.isActive(token), Role.Company) &&
                coupon.isPresent() && userToUpdate.isPresent()) {
            List<Coupon> coupons = (List<Coupon>) userToUpdate.get().getCupons();
            coupons.add(coupon.get());
            return ResponseEntity.status(HttpStatus.OK).body(coupons);
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity addCoupon(Coupon coupon, String token) {

        if (utils.checkRole(utils.isActive(token), Role.Company)) {

            if (couponRepository.findById(coupon.getId()).isPresent()) {
                return utils.getResponseEntitySomesingWrong();
            } else {
                couponRepository.save(coupon);
                Income income = new Income();
                Optional<User> userToUpdate = userRepository.findById(utils.isActive(token).getFacade().getId());
                income.setName(userToUpdate.get().getUserName());
                income.setDate(new java.util.Date(System.currentTimeMillis()));
                income.setDescription(Description.Company_Add_Coupon);
                income.setAmount(100);
                income.setRole(userToUpdate.get().getRole());
                income.setUserId(utils.isActive(token).getFacade().getId());
                incomeService.storeIncome(income);

                userToUpdate.get().setAmount(userToUpdate.get().getAmount() - 100);
                List<Coupon> coupons = (List<Coupon>) userToUpdate.get().getCupons();
                coupons.add(coupon);
                userToUpdate.get().setCupons(coupons);
                userRepository.save(userToUpdate.get());
                return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
            }
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity findAllCouponsByUser(String token) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    userRepository.findById(utils.isActive(token).getFacade().getId()).get().getCupons());
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity deleteCouponByUser(String token, long coupId) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List<Coupon> list = user.get().getCupons().stream().filter(coupon ->
                    coupon.equals(couponRepository.findById(coupId).get())).collect(Collectors.toList());
            user.get().setCupons(list);
            userRepository.save(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(
                    userRepository.findById(user.get().getId()).get().getCupons());
        }
        return utils.getResponseEntitySesionNull();
    }


    public ResponseEntity deleteCouponsByUser(String token) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            user.get().setCupons(null);
            userRepository.save(user.get());
            return ResponseEntity.status(HttpStatus.OK).body("All coupons deleted");
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity updateCoupon(Coupon coupon, String token) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> company = userRepository.findById(utils.isActive(token).getFacade().getId());
            Coupon compCoupon = company.get().getCupons().stream()
                    .filter(coupon1 -> coupon.getId() == (coupon1.getId()))
                    .findAny()
                    .orElse(null);
            if (compCoupon != null) {
                Optional<Coupon> coupToUpdate = couponRepository.findById(coupon.getId());
                coupToUpdate.get().setEndDate(coupon.getEndDate());
                coupToUpdate.get().setPrice(coupon.getPrice());

                Income income = new Income();
                income.setName(company.get().getUserName());
                income.setDate(new java.util.Date(System.currentTimeMillis()));
                income.setDescription(Description.Company_update_Coupon);
                income.setAmount(10);
                income.setUserId(company.get().getId());
                income.setRole(Role.Company);
                incomeService.storeIncome(income);

                company.get().setAmount(company.get().getAmount() - 10);
                userRepository.save(company.get());

                couponRepository.save(coupToUpdate.get());


                return ResponseEntity.status(HttpStatus.OK).body(
                        couponRepository.findById(coupon.getId()).get());
            }
            return utils.getResponseEntitySomesingWrong();
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserCouponByType(String token, String type) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List sorted = user.get().getCupons().stream().
                    filter(coupon -> (coupon.getType().toString()).equals(type)).
                    collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserCouponByDateBefore(String token, Date date) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List sorted = user.get().getCupons().stream().
                    filter(coupon -> (coupon.getEndDate().before(date))).
                    collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserCouponByPriceLessThat(String token, double price) {
        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List sorted = user.get().getCupons().stream().
                    filter(coupon -> (coupon.getPrice() < price)).
                    collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity findCoupfromUserbyCouponId(String token, long coupId) {
        Optional<User> comp = userRepository.findById(utils.isActive(token).getFacade().getId());
        if (utils.checkRole(utils.isActive(token), Role.Company) && comp.isPresent()) {
            Collection<Coupon> coupons = comp.get().getCupons();
            if (coupons.contains(couponRepository.findById(coupId).get())) {
                return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findById(coupId).get());
            }
            return utils.getResponseEntitySomesingWrong();
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity viewIncomes(String token) {
        return incomeService.vievIncomeByCompany(token);
    }

    public ResponseEntity getAllCouponsOfAllCompanys(String token) {

        if (utils.checkRole(utils.isActive(token), Role.Company)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
        }
        return utils.getResponseEntitySesionNull();
    }
}
