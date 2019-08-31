package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;

public interface CouponRepository extends JpaRepository<Coupon, Long>{

	Iterable<Coupon> findByOne(CouponType type);



}
