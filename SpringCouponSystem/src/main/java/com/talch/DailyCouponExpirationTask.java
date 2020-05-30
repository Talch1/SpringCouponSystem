package com.talch;


import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.talch.beans.Coupon;
import com.talch.repo.CouponRepository;

@Component
@Async
@RequiredArgsConstructor
public class DailyCouponExpirationTask extends Thread {

   private final CouponRepository couponRepository;

    @Override
    public void run() {

        List<Coupon> coupons = couponRepository.findAll();
        coupons.stream().filter(coupon -> coupon.getEndDate().
                before(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))).
                collect(Collectors.toList());
        couponRepository.deleteAll(coupons);
        System.out.println("all Coupon before date " + new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24) + " was deleted");
        try {
            Thread.sleep(1000 * 24 * 60 * 60);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
