package com.blog.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.blogappapis.configs.AppConstants;
import com.blog.blogappapis.entities.Role;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.payloads.UserDto;
import com.blog.blogappapis.repositories.RoleRepo;
import com.blog.blogappapis.repositories.UserRepository;
import com.blog.blogappapis.services.UserService;
import com.blog.blogappapis.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		return registerNewUser(userDto);
		/*User user = this.dtoToUser(userDto);
		User userSaved = this.userRepo.save(user);
		
		// TODO Auto-generated method stub
		return this.userToDto(userSaved);*/
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		System.out.println("updating password");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User userSaved = this.userRepo.save(user);
		return this.userToDto(userSaved);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto,User.class);
		//User user = new User();
		//user.setId(userDto.getId());
		//user.setName(userDto.getName());
		//user.setEmail(userDto.getEmail());
		//user.setPassword(userDto.getPassword());
		//user.setAbout(userDto.getAbout());
		return user;
	}
	
	private UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		//UserDto userDto = new UserDto();
		//userDto.setId(user.getId());
		//userDto.setName(user.getName());
		//userDto.setEmail(user.getEmail());
		//userDto.setPassword(user.getPassword());
		//userDto.setAbout(user.getAbout());
		return userDto;
	}
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		System.out.println("we have user with password : "+user.getPassword());

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		System.out.println("we have user with new password : "+user.getPassword());

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);
		System.out.println("savind user");

		User newUser = this.userRepo.save(user);
		System.out.println("user saved");
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
