package com.talch.rest;

import com.talch.CouponSystem;
import com.talch.beans.CouponType;
import com.talch.facade.CustomerFacade;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerFacade customerService;

    private final CouponSystem system;

    private final Utils utils;

    // http://localhost:8081/v1/admin/logout
    @PostMapping(value = "/logout")
    private void logout(@RequestHeader String token) {
        utils.getSystem().getTokensMap().remove(token);
    }

    // http://localhost:8081/v1/customer/seeAllCoupons
    @GetMapping(value = "/seeAllCoupons")
    public ResponseEntity seeAllCoup(@RequestHeader String token) {
        return customerService.getAllCouponsOfAllCompanys(token);
    }

    // http://localhost:8081/v1/customer/addCouponToCust/{coupId}
    @PutMapping(value = "/addCouponToCust/{coupId}")
    public ResponseEntity<?> addCoupon(@RequestHeader String token, @PathVariable long coupId) {
        return customerService.addCouponToUser(token, coupId);
    }

    // http://localhost:8081/v1/customer/getCustCoup
    @GetMapping(value = "/getCustCoup")
    public ResponseEntity<?> getCustCoupons(@RequestHeader String token) {
        return customerService.getAllcouponsByUserId(token);
    }

    // http://localhost:8081/v1/customer/getCustCoupByID/{coupId}
    @GetMapping(value = "/getCustCoupByID/{coupId}")
    public ResponseEntity<?> findCustCoupById(@RequestHeader String token, @PathVariable long coupId) {
        return customerService.getCouponByUserId(token, coupId);
    }

    // http://localhost:8081/v1/customer/findCustCoupByType/{type}
    @GetMapping(value = "/findCustCoupByType/{type}")
    public ResponseEntity<?> findCustCoupByType(@RequestHeader String token, @PathVariable CouponType type) {
        return customerService.getUserCouponByType(token, type);
    }

    // http://localhost:8081/v1/customer/findCustCoupByDate/{date}
    @GetMapping(value = "/findCustCoupByDate/{date}")
    public ResponseEntity<?> findCustCoupByDate(@RequestHeader String token, @PathVariable Date date) {
        return customerService.getUserCouponByDateBefore(token, date);
    }

    // http://localhost:8081/v1/customer/findCustCoupByPrice/{price}
    @GetMapping(value = "/findCustCoupByPrice/{price}")
    public ResponseEntity<?> findCustCoupByPrice(@RequestHeader String token, @PathVariable double price) {
        return customerService.getUserCouponByPriceLessThat(token, price);
    }
}