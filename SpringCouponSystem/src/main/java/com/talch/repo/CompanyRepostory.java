package com.talch.repo;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Company;



@Repository
public interface CompanyRepostory extends JpaRepository<Company, Long> {

	public Company findByCompNameAndPassword(String compName,String password);

	
	
	

}
