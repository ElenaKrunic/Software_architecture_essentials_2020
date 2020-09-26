package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);

}
