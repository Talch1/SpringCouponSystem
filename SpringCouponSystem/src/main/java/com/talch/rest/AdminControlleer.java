package com.talch.rest;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.facade.AdminFacade;
import com.talch.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/")
public class AdminControlleer {


    private final AdminFacade adminService;

    private final Utils utils;

    // http://localhost:8081/v1/admin//logout
    @PostMapping(value = "/logout")
    private void logout(@RequestHeader String token) {
        utils.getSystem().getTokensMap().remove(token);
    }
//******************************Customer**********************************

    // http://localhost:8081/v1/admin/customerCreate
    @PostMapping(value = "/customerCreate")
    public ResponseEntity<?> customerCreate(@RequestBody User customer, @RequestHeader String token) {
        return adminService.insertUser(customer, token);
    }

    // http://localhost:8081/v1/admin/deleteCust/{custId}}
    @DeleteMapping(value = "/deleteCust/{custId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long custId, @RequestHeader String token) {
        return adminService.deleteUserById(custId, token);
    }

    // http://localhost:8081/v1/admin/custUpdate/{id}
    @PutMapping(value = "/custUpdate/{id}")
    public ResponseEntity<?> updateCustomer1(@PathVariable long id, @RequestBody User customer,
                                             @RequestHeader String token) {
        return adminService.updateUser(id, customer, token);
    }

    // http://localhost:8081/v1/admin/getCustByID/{custId}
    @GetMapping(value = "/getCustByID/{custId}")
    public ResponseEntity<?> findById(@PathVariable long custId, @RequestHeader String token) {
        return adminService.getUserById(custId, token);
    }

    // http://localhost:8081/v1/admin/getCustomers
    @GetMapping(value = "/getCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {
        return adminService.findAllCust(token);
    }

    // http://localhost:8081/v1/admin/deleteCust/All
    @DeleteMapping(value = "/deleteCust/All")
    public ResponseEntity<String> deleteCustomers(@RequestHeader String token) {
        return adminService.deleteAllUsers(token, Role.Customer);
    }

    // ***********************Company***************************************

    // http://localhost:8081/v1/admin/companyCreate
    @PostMapping(value = "/companyCreate")
    public ResponseEntity<?> companyCreate(@RequestBody User company, @RequestHeader String token) {
        return adminService.insertUser(company, token);
    }

    // http://localhost:8081/v1/admin/deleteComp/{compId}
    @DeleteMapping(value = "/deleteComp/{compId}")
    public ResponseEntity<?> deleteCompany(@PathVariable long compId, @RequestHeader String token) {
        return adminService.deleteUserById(compId, token);
    }

    // http://localhost:8081/v1/admin/companyUpdate
    @PutMapping(value = "/companyUpdate/{id}")
    public ResponseEntity<?> companyUpdate(@PathVariable long id, @RequestBody User company,
                                           @RequestHeader String token) {
        return adminService.updateUser(id, company, token);
    }

    // http://localhost:8081/v1/admin/getCompByID/{compId}
    @GetMapping(value = "/getCompByID/{compId}")
    public ResponseEntity<?> getCompByID(@PathVariable long compId, @RequestHeader String token) {
        return adminService.getUserById(compId, token);

    }

    // http://localhost:8081/v1/admin/getCompanys
    @GetMapping(value = "/getCompanys")
    public ResponseEntity<?> companys(@RequestHeader String token) {
        return adminService.findAllComp(token);

    }

    // http://localhost:8081/v1/admin/deleteComp/All
    @DeleteMapping(value = "/deleteComp/All")
    public ResponseEntity<String> deleteCompanys(Role role, @RequestHeader String token) {
        return adminService.deleteAllUsers(token, role);
    }

    // http://localhost:8081/v1/admin/getComByNameAndPass
    @GetMapping(value = "/getComByNameAndPass")
    public ResponseEntity<?> getCompanyByNameAndPass(@RequestParam String name, @RequestParam String pass,
                                                     @RequestHeader String token) {
        return adminService.getUserByNameAndPass(name, pass, Role.Company, token);
    }

    // http://localhost:8081/v1/admin/addCouponToComp
    @PutMapping(value = "/addCouponToComp/{userId}")
    public ResponseEntity<?> addCouponsToComp(@PathVariable long userId, @RequestBody long couponId,
                                              @RequestHeader String token) {
        return adminService.addCouponToUser(userId, couponId, token);
    }

    // http://localhost:8081/v1/admin/getCompCoupons
    @GetMapping(value = "/getCompCoupons/{id}")
    public ResponseEntity<?> getCompCoupons(@PathVariable long id, @RequestHeader String token) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService
                .getAllcouponsByUserId(id, token));
    }

    // ***********************Coupon*****************************************

    // http://localhost:8081/v1/admin/createCoup
    @PostMapping(value = "/createCoup")
    public ResponseEntity<?> insertCoup(@RequestBody Coupon coup, @RequestHeader String token) {
        return adminService.addCoupon(coup, token);
    }

    // http://localhost:8081/v1/admin/getCoupByID/{id}
    @GetMapping(value = "/getCoupByID/{id}")
    public ResponseEntity<?> findById2(@PathVariable Long id, @RequestHeader String token) {
        return adminService.findCoupById(id, token);
    }

    // http://localhost:8081/v1/admin/getCoupons
    @GetMapping(value = "/getCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader String token) {
        return adminService.findAllCoup(token);

    }

    // http://localhost:8081/v1/admin/deleteCoup/{id}
    @DeleteMapping(value = "/deleteCoup/{id}")
    public ResponseEntity<?> deleteCoup(@PathVariable Long id, @RequestHeader String token) {
        return adminService.deleteCoupon(id, token);
    }

    // http://localhost:8081/v1/admin/deleteCoup/All
    @DeleteMapping(value = "/deleteCoup/All")
    public ResponseEntity<String> deleteCoupons(@RequestHeader String token) {
        return adminService.deleteCoupons(token);
    }

    // http://localhost:8081/v1/admin/coupUpdate
    @PutMapping(value = "/coupUpdate")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
        return adminService.updateCouponAdmin(coupon, token);
    }

    // http://localhost:8081/v1/admin/getAllCoupByType
    @GetMapping(value = "/getAllCoupByType/{type}")
    public ResponseEntity<?> getAllCouponsByType(@PathVariable CouponType type, @RequestHeader String token) {
        return adminService.getCouponByType(type, token);

    }

    // http://localhost:8081/v1/admin/getAllCoupByDate
    @GetMapping(value = "/getAllCoupByDate/{date}")
    public ResponseEntity<?> getAllCouponsByDate(@PathVariable Date date, @RequestHeader String token) {
        return adminService.getCouponByDate(date, token);

    }

    // http://localhost:8081/v1/admin/getAllCoupByPrice
    @GetMapping(value = "/getAllCoupByPrice/{price1}")
    public ResponseEntity<?> getCouponWhenPriceBetwenPrice(@PathVariable Double price1, @RequestHeader String token) {
        return adminService.getCouponWhenPriceBetwenPrice(price1, token);
    }
}
