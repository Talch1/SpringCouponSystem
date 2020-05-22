package com.talch.repo;






import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.talch.beans.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUserNameAndPassword(String userName, String password);



	
}
