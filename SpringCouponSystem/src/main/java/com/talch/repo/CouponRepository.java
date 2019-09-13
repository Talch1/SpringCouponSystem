package com.talch.repo;


import java.sql.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{

	
	public  List<Coupon> findByType(CouponType type) ;
	
	public List<Coupon> findByPriceLessThan(Double price1);
	
	public  List<Coupon> findByEndDateBefore(Date date) ;
	
	

	

}
