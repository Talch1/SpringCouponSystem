package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{





}
