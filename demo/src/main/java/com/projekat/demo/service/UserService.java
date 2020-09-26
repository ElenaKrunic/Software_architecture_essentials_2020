package com.projekat.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.User;
import com.projekat.demo.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository; 
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
	   
	public User findByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}
}
