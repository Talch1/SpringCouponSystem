package com.talch.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.ClientType;
import com.talch.beans.Company;
import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Customer;
import com.talch.beans.User;
import com.talch.repo.CompanyRepostory;
import com.talch.repo.CouponRepository;
import com.talch.repo.CustomerRepository;
import com.talch.repo.UserRepo;

@Service
@Transactional
public class AdminService {

	ClientType clientType = ClientType.Admin;

	@Autowired
	CompanyRepostory companyRepostory;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CouponRepository couponRepository;
	@Autowired
	UserRepo userRepo;

	Date date = new Date(System.currentTimeMillis());
	Date datePlus5Days = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 5));
	Date datePlus1Min = new Date(System.currentTimeMillis() + (1000 * 60));

	CouponType coupType = CouponType.SPORTS;
	CouponType coupType2 = CouponType.HEALTH;
	CouponType coupType3 = CouponType.RESTURANS;

	public String getClientType() {
		return clientType.name();
	}

	@PostConstruct
	public void initDBCoup() {
        
		companyRepostory.deleteAll();
		customerRepository.deleteAll();
		couponRepository.deleteAll();

		List<User> user = new ArrayList<>();
		user.add(new User(11,"Kia","kiamotors",ClientType.Company));
		user.add(new User(12,"CocaCola","cola",ClientType.Company));
		user.add(new User(13,"Osem","bisli",ClientType.Company));
		user.add(new User(14,"Gabi","1985",ClientType.Customer));
		user.add(new User(15,"Tomer","ppp",ClientType.Customer));
		user.add(new User(16,"Vasya","GOD",ClientType.Customer));

		userRepo.saveAll(user);

		List<Coupon> coup = new ArrayList<>();

		coup.add(new Coupon(1582, "1+1", date, datePlus5Days, 5, coupType, "just now!", 50.3,
				"www.gvdjshbs.com/xjh.gif"));
		coup.add(new Coupon(12, "2+1", date, datePlus5Days, 5, coupType2, "just today!", 100.7,
				"www.wcdvsjv.com/xjh.gif"));
		coup.add(new Coupon(82, "second helf price", date, datePlus1Min, 5, coupType3, "Sale!", 25.6,
				"www.gvdjsjznalnhbs.com/xjh.gif"));

		couponRepository.saveAll(coup);

		List<Company> comp = new ArrayList<>();
		comp.add(new Company(1,new User(11,"Kia","kiamotors",ClientType.Company),"kiamotors@kiamotors.net", null));
		comp.add(new Company(2,new User(12,"CocaCola","cola",ClientType.Company), "Cola@cola.net", null));
		comp.add(new Company(3,new User(13,"Osem","bisli",ClientType.Company),  "osem@osem.com", null));

		companyRepostory.saveAll(comp);

		List<Customer> cust = new ArrayList<>();

		cust.add(new Customer(4,new User(14,"Gabi","1985",ClientType.Customer),  null));
		cust.add(new Customer(5,new User(15,"Tomer","ppp",ClientType.Customer),  null));
		cust.add(new Customer(6,new User(16,"Vasya","GOD",ClientType.Customer),  null));

		customerRepository.saveAll(cust);
	}
//**************************Company************************************

	public List<Company> insertCompany(Company company) {
		companyRepostory.save(company);
		return companyRepostory.findAll();
	}

	public List<Company> deleteCompanyfromCompany(Long id) {
		companyRepostory.deleteById(id);
		return companyRepostory.findAll();

	}

	public String deleteCompanys() {
		companyRepostory.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Company> geCompany(Long id) {
		return companyRepostory.findById(id);
	}

	public List<Company> findAllCom() {
		return companyRepostory.findAll();
	}

	public Company updateCompany(Long id, Company company) {
		Company compToUpdate = companyRepostory.getOne(id);
		compToUpdate.setName(company.getUser().getName());
		compToUpdate.setEmail(company.getEmail());
		compToUpdate.setPassword(company.getUser().getPassword());
		companyRepostory.save(compToUpdate);
		return compToUpdate;

	}
	public Company getCompanyByNameAndPass(String name,String pass) {
		User user = userRepo.findByNameAndPassword(name, pass);
		List<Company> companys = companyRepostory.findAll();
		Company c = new Company();
		for (Company company : companys) {
			if (company.getUser().getUserId()== user.getUserId()) {
				c = company;
			}
			
		}
		return c;
		
}
	public void addCoupons(long id, List<Coupon> coupons) {
		Company compToUpdate = companyRepostory.getOne(id);
		compToUpdate.setCupons(coupons);
		companyRepostory.save(compToUpdate);

	}

	public Collection<Coupon> getAllcouponsByCompId(long id) {
		Optional<Company> company = companyRepostory.findById(id);
		List<Coupon> coupons = (List<Coupon>) company.get().getCupons();
		return coupons;

	}
	// **************************Customer************************************

	public List<Customer> deleteCustomer(Long id) {
		customerRepository.deleteById(id);
		return customerRepository.findAll();
	}

	public String deleteCustomers() {
		customerRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public List<Customer> findAllCust() {
		return customerRepository.findAll();
	}

	public List<Customer> insertCust(Customer customer) {
		customerRepository.save(customer);
		return customerRepository.findAll();
	}

	public Customer updateCustomer(long id, Customer customer) {
		Customer custToUpdate = customerRepository.getOne(id);
		custToUpdate.setCustName(customer.getUser().getName());
		custToUpdate.setPassword(customer.getUser().getName());
		customerRepository.save(custToUpdate);
		return custToUpdate;

	}

	public void purchoiseCoupon(long custId,Coupon coupon ) {
		Customer custToUpdate = customerRepository.getOne(custId);
		List<Coupon> coupons= (List<Coupon>) custToUpdate.getCupons();
		coupons.add(coupon);
		customerRepository.save(custToUpdate);

	}

	public Collection<Coupon> getAllcouponsByCustId(long id) {
		Optional<Customer> cust = customerRepository.findById(id);
		List<Coupon> coupons = (List<Coupon>) cust.get().getCupons();
		return coupons;

	}
	// **************************CouponS************************************

	public List<Coupon> addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
		return couponRepository.findAll();
	}

	public List<Coupon> deleteCoupon(long id) {
		couponRepository.deleteById(id);
		return couponRepository.findAll();
	}

	public Coupon updateCoupon(long id, Coupon coupon) {
		Coupon coupToUpdate = couponRepository.getOne(id);
		coupToUpdate.setEndDate(coupon.getEndDate());

		coupToUpdate.setPrice(coupon.getPrice());
		couponRepository.save(coupToUpdate);

		return coupToUpdate;

	}

	public String deleteCoupons() {
		couponRepository.deleteAll();
		return "All Customers Deleted ";
	}

	public Optional<Coupon> findCoupById(Long id) {
		return couponRepository.findById(id);
	}

	public List<Coupon> findAllCoup() {
		return couponRepository.findAll();
	}

	public List<Coupon> getCouponByType(CouponType type) {
		return couponRepository.findByType(type);
	}

	public List<Coupon> getCouponByDate(Date date) {

		return couponRepository.findByEndDateBefore(date);

	}

	public List<Coupon> getCouponWhenPriceBetwenPrice(Double price1) {
		return couponRepository.findByPriceLessThan(price1);

	}

	public boolean loggin(String name, String password, String clientType2) {
		if (name.equals("admin")&&password.equals("1234")&& clientType2.equals(ClientType.Admin.name())) {
			return true;
		}
		return false;
	}

}