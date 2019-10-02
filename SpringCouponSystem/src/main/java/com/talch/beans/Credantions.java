package com.talch.beans;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Credantions {
	String userName;
	String password;
	String type;
	

}
