package com.blog.blogappapis.services;

import java.util.List;

import javax.validation.Valid;

import com.blog.blogappapis.payloads.UserDto;


public interface UserService {
	
	 UserDto createUser(UserDto user);
	 UserDto updateUser(UserDto user,Integer userId);
	 UserDto getUserById(Integer userId);
	 List<UserDto> getAllUsers();
	 
	 void deleteUser(Integer userId);
	UserDto registerNewUser(@Valid UserDto userDto);

}
