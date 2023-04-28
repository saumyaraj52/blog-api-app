package com.blog.blogappapis.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtAuthResponse {

	private String token;
	private UserDto user;

	public void setUser(UserDto user) {
		// TODO Auto-generated method stub
		this.user = user;
		
	}

	public void setToken(String token) {
		// TODO Auto-generated method stub
		this.token = token;
	}
	public String getToken() {
		return this.token;
	}

	public UserDto getUser() {
		return this.user;
	}

	public JwtAuthResponse() {}
}
