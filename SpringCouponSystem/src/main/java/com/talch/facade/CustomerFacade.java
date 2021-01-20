package com.talch.facade;

import com.talch.beans.*;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;
import com.talch.service.IncomeService;
import com.talch.utils.Utils;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
@Scope("prototype")
public class CustomerFacade implements Facade {

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    private final IncomeService incomeService;

    private final Utils utils;

    private long id;
    private String custName;
    private Role role = Role.Customer;

    public ResponseEntity addCouponToUser(String token, long coupId) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            Optional<Coupon> coupon = couponRepository.findById(coupId);
            Optional<User> customer = userRepository.findById(utils.isActive(token).getFacade().getId());
            List<Coupon> coupons = (List<Coupon>) customer.get().getCupons();
            if (coupons.contains(coupon.get())) {
                return utils.getResponseEntitySomesingWrong();
            }
            coupons.add(coupon.get());
            Income income = new Income();
            income.setName(customer.get().getUserName());
            income.setDate(new java.util.Date(System.currentTimeMillis()));
            income.setDescription(Description.Bay_Coupon_By_User);
            income.setAmount(coupon.get().getPrice());
            income.setRole(customer.get().getRole());
            income.setUserId(customer.get().getId());

            double amountTmp = customer.get().getAmount();
            customer.get().setAmount(amountTmp - coupon.get().getPrice());

            incomeService.storeIncome(income);
            userRepository.save(customer.get());

        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getAllcouponsByUserId(String token) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {

            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            return   ResponseEntity.status(HttpStatus.OK).body(user.get().getCupons());
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getCouponByUserId(String token, long coupId) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            Optional<Coupon> coupon = couponRepository.findById(coupId);
            if (user.get().getCupons().contains(coupon.get())) ;
            {
                return ResponseEntity.status(HttpStatus.OK).body(coupon.get());
            }
        }
        return utils.getResponseEntitySomesingWrong();
    }

    public ResponseEntity getUserCouponByPriceLessThat(String token, double price) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List<Coupon> sorted = user.get().getCupons().stream()
                    .filter(coupon -> coupon.getPrice() < price)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserCouponByType(String token, CouponType type) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List<Coupon> sorted = user.get().getCupons().stream()
                    .filter(coupon -> coupon.getType().equals(type))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getUserCouponByDateBefore(String token, Date date) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            Optional<User> user = userRepository.findById(utils.isActive(token).getFacade().getId());
            List<Coupon> sorted = user.get().getCupons().stream()
                    .filter(coupon -> coupon.getEndDate().before(date))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(sorted);
        }
        return utils.getResponseEntitySesionNull();
    }

    public ResponseEntity getAllCouponsOfAllCompanys(String token) {
        if (utils.checkRole(utils.isActive(token), Role.Customer)) {
            return ResponseEntity.status(HttpStatus.OK).body(couponRepository.findAll());
        }
        return utils.getResponseEntitySesionNull();
    }
}
