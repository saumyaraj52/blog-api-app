package com.blog.blogappapis.controllers;

import java.security.Principal;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.payloads.JwtAuthRequest;
import com.blog.blogappapis.payloads.JwtAuthResponse;
import com.blog.blogappapis.payloads.UserDto;
import com.blog.blogappapis.repositories.UserRepository;
import com.blog.blogappapis.security.JwtTokenHelper;
import com.blog.blogappapis.services.UserService;
import com.blog.blogappapis.exceptions.ApiException;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		System.out.println("token is here");
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		System.out.println("token is set");
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		System.out.println("user is set");
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			System.out.println("autheticate step1");
			System.out.println(username+"   ::   "+password);
			this.authenticationManager.authenticate(authenticationToken);
			System.out.println("autheticate step2");

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials Part1!!");
			throw new ApiException("Invalid username or password !!");
		}
		catch(InternalAuthenticationServiceException e)
		{
			System.out.println("Invalid Detials Part2!!");
			throw new ApiException("Invalid username or password !!");
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
		System.out.println("adding user step1");
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		System.out.println("Done");
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	// get loggedin user data
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName()).get();
		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}

}
