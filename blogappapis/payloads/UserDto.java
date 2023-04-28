package com.blog.blogappapis.payloads;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.blogappapis.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min = 4,message="UserName  must be of min 4 characters!!")
	private String name;
	
	@Email(message ="Your email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max=10,message="Password  must be of min 4 chars and max of 10 chars!!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto>roles = new HashSet<>();

	public Set<RoleDto> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
}
