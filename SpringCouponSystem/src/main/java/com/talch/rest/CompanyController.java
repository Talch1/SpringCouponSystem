package com.talch.rest;

import java.sql.Date;

import com.talch.service.CouponSystem;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talch.beans.Coupon;

import com.talch.facade.CompanyFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company")
@CrossOrigin("*")
public class CompanyController {

    private final CompanyFacade companyFacade;

    private final CouponSystem system;

    private final Utils utils;

    // http://localhost:8081/v1/company/logout
    @PostMapping(value = "/logout")
    private void logout(@RequestHeader String token) {
        utils.getTokensMap().remove(token);
    }

    // http://localhost:8081/v1/company/seeAllCoupons
    @GetMapping(value = "/seeAllCoupons")
    public ResponseEntity<?> seeAllCoup(@RequestHeader String token) {
        return companyFacade.getAllCouponsOfAllCompanys(token);
    }

    // http://localhost:8081/v1/company/addCouponToComp
    @PostMapping(value = "/addCouponToComp")
    public ResponseEntity<?> addCouponsToComp(@RequestHeader String token, @RequestBody long coupId) {
        return companyFacade.addCouponToUser(coupId, token);
    }

    // http://localhost:8081/v1/company/createCoup
    @PostMapping(value = "/createCoup")
    public ResponseEntity<?> insertCoup(@RequestBody Coupon coup, @RequestHeader String token) {
        return companyFacade.addCoupon(coup, token);
    }

    // http://localhost:8081/v1/company/getCoupByID/{coupId}
    @GetMapping(value = "/getCoupByID/{coupId}")
    public ResponseEntity<?> getCoupByID(@RequestHeader String token, @PathVariable long coupId) {
        return companyFacade.findCoupfromUserbyCouponId(token, coupId);
    }

    // http://localhost:8081/v1/company/deleteCouponById/{coupId}
    @DeleteMapping(value = "/deleteCouponById/{coupId}")
    public ResponseEntity<?> deleteCouponById(@RequestHeader String token, @PathVariable long coupId) {
        return companyFacade.deleteCouponByUser(token, coupId);
    }

    // http://localhost:8081/v1/company/getCoupons"
    @GetMapping(value = "/getCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader String token) {
        return companyFacade.findAllCouponsByUser(token);
    }

    // http://localhost:8081/v1/company/deleteCoup/All/
    @DeleteMapping(value = "/deleteCoup/All")
    public ResponseEntity<?> deleteCoupons(@RequestHeader String token) {
        return companyFacade.deleteCouponsByUser(token);
    }

    // http://localhost:8081/v1/company/coupUpdate
    @PutMapping(value = "/coupUpdate")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
        return companyFacade.updateCoupon(coupon, token);
    }

    // http://localhost:8081/v1/company/findUserCoupByType{type}
    @GetMapping(value = "/findUserCoupByType/{type}")
    public ResponseEntity<?> findUserCoupByType(@RequestHeader String token, @PathVariable String type) {
        return companyFacade.getUserCouponByType(token, type);
    }

    // http://localhost:8081/v1/company/findUserCoupByDate/{date}
    @GetMapping(value = "/findUserCoupByDate/{date}")
    public ResponseEntity<?> findUserCoupByDate(@RequestHeader String token, @PathVariable Date date) {
        return companyFacade.getUserCouponByDateBefore(token, date);
    }

    // http://localhost:8081/v1/company/findUserCoupByPrice/{price}
    @GetMapping(value = "/findUserCoupByPrice/{price}")
    public ResponseEntity<?> findUserCoupByPrice(@RequestHeader String token, @PathVariable double price) {
        return companyFacade.getUserCouponByPriceLessThat(token, price);
    }
}
