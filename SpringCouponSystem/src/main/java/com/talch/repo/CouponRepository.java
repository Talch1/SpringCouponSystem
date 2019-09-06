package com.talch.repo;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{

	Date date = new Date(System.currentTimeMillis());

	
//	
//
//	public List<Coupon> findAllWhereEndDateAfterDate(Date date);

}
