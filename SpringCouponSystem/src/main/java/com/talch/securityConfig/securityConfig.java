package com.talch.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@ComponentScan("com.talch")
@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 
		http
		
		.authorizeRequests()
		.antMatchers("/admin/**").permitAll()
		.antMatchers("/comp/**").permitAll()
		.antMatchers("/cust/**").permitAll()
		.antMatchers("/AdminFacade/**").permitAll()
		.antMatchers("/CompanyFacade/**").permitAll()
		.antMatchers("/CustomerFacade/**").permitAll()
		.and()
		.logout().permitAll().logoutSuccessUrl("/login")
		.and()
		.formLogin().usernameParameter("name")		
		.loginPage("/login");
		  
		http.csrf().disable();
	}

	
	
}
