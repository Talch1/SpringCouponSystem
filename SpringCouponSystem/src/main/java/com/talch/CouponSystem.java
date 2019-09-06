package com.talch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponSystem {
	
	DailyCouponExpirationTask dailyCouponExpirationTask;


//	public class CouponSystem {
//
//		
//		Thread thread = new Thread(new DailyCouponExpirationTask());
//		Database database = new Database();
//
//		// Constructor
//		private CouponSystem() {
//			
//		}
//
//		// Get instance
//		public static CouponSystem getInstanse() {
//			return instanse;
//		}
//
//		// Stop Thread and return all connections to blockingQueue
//		public void shutdown() {
//			DailyCouponExpirationTask.stopp();
//			ConnectionPool.getInstance().removeAllConnections();
//		}
//	//Delete all tables
//		public void name() throws SQLException, InterruptedException, SizeEx {
//			database.dropAllTables();
//		}
//		// Create All tables
//		public void createAllTables() throws SQLException, InterruptedException, SizeEx {
//
//			database.createAllTables();
//
//		}
//
//		public void start() {
//			thread.start();
//		}
//
//		// Login to Facades
//		public CouponClientFasade login(String name, String password, ClientType clientType)
//				throws LoginEx, InterruptedException, SizeEx {
//			AdminFacade adminFacade = new AdminFacade();
//			CompanyFacade companyFacade = new CompanyFacade();
//			CustomerFacade customerFacade = new CustomerFacade();
//			if (clientType.name().equals("Admin")) {
//
//				return adminFacade.login(name, password, clientType);
//
//			} else if (clientType.name().equals("Company")) {
//				return companyFacade.login(name, password, clientType);
//			} else if (clientType.name().equals("Customer")) {
//				return customerFacade.login(name, password, clientType);
//			}
//			return null;
//		}

}
