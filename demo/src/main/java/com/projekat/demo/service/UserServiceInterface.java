package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.User;

public interface UserServiceInterface {
	public User findByUsername(String username);
	
	public User findByUsernameAndPassword(String username, String password);
	
	List<User> findAll(); 
	
	public User findOne(Integer id);
}
