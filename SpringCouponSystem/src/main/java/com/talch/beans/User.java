package com.talch.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
private long userId;
	private String name;
	private String password;
	ClientType clientType;

	@Id
	public long getUserId() {
		return userId;
	}
	@Column(unique = true)
	public String getName() {
		return name;
	}

	@Column
	public String getPassword() {
		return password;
	}

	@Enumerated()
	public ClientType getClientType() {
		return clientType;
	}

}
