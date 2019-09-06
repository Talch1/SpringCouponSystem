package com.talch;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;



import com.talch.service.AdminService;
@Async
public class DailyCouponExpirationTask implements Runnable  {

	@Autowired
	AdminService service;
Date date = new Date(System.currentTimeMillis());

	volatile static boolean stop = true;

@Override
	public void run() {
	
		while (stop) {
			
	//		service.findAllWhereEndDateAfterDate(date);
					//	DELETE FROM new.coupon WHERE end_date < '2019-09-07 08:21:22'
							
						
				try {
					Thread.sleep(1000 * 60 );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}
}

public static void stopp() {
	stop = false;
	}



}
