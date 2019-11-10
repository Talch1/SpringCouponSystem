package com.talch.repo;





import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.talch.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserNameAndPassword(String custName,String password);

	public void save(Optional<User> user);


	
}
