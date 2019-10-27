package com.talch;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.talch.beans.Coupon;
import com.talch.repo.CouponRepository;
@Async
public class DailyCouponExpirationTask implements Runnable  {

	@Autowired
	CouponRepository couponRepository;
	
	
Date date = new Date(System.currentTimeMillis());

	volatile static boolean stop = true;

@Override
	public void run() {
	
		while (stop) {
			
	List<Coupon> coupons = couponRepository.findByEndDateBefore(date);
    couponRepository.deleteAll(coupons);
						
				try {
					Thread.sleep(1000 );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}
}

public static void stop() {
	stop = false;
	}



}
