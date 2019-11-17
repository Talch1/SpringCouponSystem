package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Income;
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>{

}
