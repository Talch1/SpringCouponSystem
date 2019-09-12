package com.talch.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
//	
//	@Query("SELECT * FROM Customer_Cupons WHERE Customer_id :=id")
//	public List<Coupon> getCoupons(long id);
}
