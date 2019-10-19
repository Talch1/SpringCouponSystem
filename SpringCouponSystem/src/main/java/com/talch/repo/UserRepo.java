package com.talch.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.talch.beans.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByNameAndPassword(String name,String password);
	
	public Optional<User> findByName(String name);
}
