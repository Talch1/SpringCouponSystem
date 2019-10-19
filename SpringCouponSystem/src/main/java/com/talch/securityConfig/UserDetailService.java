package com.talch.securityConfig;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talch.beans.User;
import com.talch.repo.UserRepo;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
	
		Optional<User> userCandidate = userRepo.findByName(name);
		if (userCandidate.isPresent()) {
			return new UserDetails(userCandidate.get());
		}else {
			throw new IllegalAccessError("User not found");
		}
		
	}

}
