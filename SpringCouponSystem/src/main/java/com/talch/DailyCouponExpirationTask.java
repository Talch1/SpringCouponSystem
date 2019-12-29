package com.talch;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.talch.beans.Coupon;
import com.talch.repo.CouponRepository;
@Component
@Async
public class DailyCouponExpirationTask extends Thread  {

	@Autowired
	CouponRepository couponRepository;
	
	
Date date = new Date(System.currentTimeMillis());


@Override
	public void run() {
	
		
			
	List<Coupon> coupons = couponRepository.findByEndDateBefore(date);
	
    for (Coupon coupon : coupons) {
    	
		couponRepository.delete(coupon);
		System.out.println("a");
	}
				try {
					Thread.sleep(1000 *24*60*60);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
}




}
