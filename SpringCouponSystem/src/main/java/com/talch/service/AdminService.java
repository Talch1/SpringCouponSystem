package com.talch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Customer;
import com.talch.repo.CompanyRepostory;
import com.talch.repo.CouponRepository;
import com.talch.repo.CustomerRepository;

@Service
@Transactional
public class AdminService {

	@Autowired
	CompanyRepostory companyRepostory;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CouponRepository couponRepository;
	
	
	Date date = new Date(System.currentTimeMillis());
	Date datePlus5Days = new Date(System.currentTimeMillis()+(1000*60*60*24*5));
	Date datePlus1Min = new Date(System.currentTimeMillis()+(1000*60));
	
	CouponType coupType = CouponType.SPORTS;
	CouponType coupType2 = CouponType.HEALTH;	
	CouponType coupType3= CouponType.RESTURANS;

@PostConstruct
public void initDB() {
	
	companyRepostory.deleteAll();
	customerRepository.deleteAll();
	couponRepository.deleteAll();
	
	List<Company> comp = new ArrayList<>();
	
	comp.add(new Company(120, "Cola", "alwayseCocaCola ","Cola@cola.net",null));
	comp.add(new Company(201, "Osem", "bisli","osem@osem.com",null));
	
	companyRepostory.saveAll(comp);
	
	List<Customer> cust = new ArrayList<>();
	
	cust.add(new Customer(2010, "Gabi", "12345" ,null));
	cust.add(new Customer(15518, "Tomer", "good",null));
	
	customerRepository.saveAll(cust);
	
	List<Coupon> coup = new ArrayList<>();
	
	coup.add(new Coupon(1582, "1+1", date ,datePlus5Days,5,coupType,"just now!",50.3,"www.gvdjshbs.com/xjh.gif"));
	coup.add(new Coupon(12, "2+1", date ,datePlus5Days,5,coupType2,"just today!",100.7,"www.wcdvsjv.com/xjh.gif"));
	coup.add(new Coupon(82, "second helf price", date ,datePlus1Min,5,coupType3,"Sale!",25.6,"www.gvdjsjznalnhbs.com/xjh.gif"));
	
	couponRepository.saveAll(coup);
	
	
	
}

}
