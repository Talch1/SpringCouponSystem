package com.talch;



import org.springframework.stereotype.Component;


@Component
public class CouponSystem {
	
		
		Thread thread = new Thread(new DailyCouponExpirationTask());
 

		
		
	public void shutdown() {
		DailyCouponExpirationTask.stop();
	}

		public void start() {
			thread.start();
		}

}
