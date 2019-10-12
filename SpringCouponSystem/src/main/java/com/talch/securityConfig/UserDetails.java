package com.talch.securityConfig;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.talch.beans.User;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{

	private User user;
	
	public UserDetails(User user) {
		super();
		this.user = user;
	}
	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String userRole = user.getClientType().name();
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole);
		return Collections.singletonList(grantedAuthority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
