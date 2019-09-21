package com.talch;


import java.sql.Date;


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
			
			service.getCouponByDate(date);
					
							
						
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
