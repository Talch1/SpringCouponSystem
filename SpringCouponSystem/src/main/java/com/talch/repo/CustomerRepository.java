package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talch.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
