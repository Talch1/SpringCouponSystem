package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talch.beans.Company;


public interface CompanyRepostory extends JpaRepository<Company, Long>{

}
