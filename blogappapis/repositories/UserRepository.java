package com.blog.blogappapis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogappapis.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	Optional<User> findByEmail(String email);

}
