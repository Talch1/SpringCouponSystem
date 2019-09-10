package com.talch.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{

	
	public  List<Coupon> getCouponByType(CouponType type) ;
	
	public List<Coupon> getCouponWhenPriceSmallerPrice1(Double price1);
	
	public  List<Coupon> getCouponByDate(Date date) ;

}
