package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.User;
import com.projekat.demo.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);


	@Autowired
	private UserRepository userRepository; 
	
	public User findByUsername(String username) {
		LOGGER.info("Metoda iz servisa za pronalazenje korisnika preko korisnickog imena"); 
		return userRepository.findByUsername(username);
	}

	public User save(User user) {
		LOGGER.info("Metoda iz servisa za cuvanje korisnika"); 
		return userRepository.save(user);
	}
	   
	public User findByUsernameAndPassword(String username, String password) {
		LOGGER.info("Metoda iz servisa za pronalazenje korisnika preko korisnickog imena i lozinke"); 
		return userRepository.findByUsernameAndPassword(username,password);	
	}

	@Override
	public List<User> findAll() {
		LOGGER.info("Metoda iz servisa za pronalazenje svih korisnika"); 
		return userRepository.findAll();
	}

	public User findOne(Integer id) {
		LOGGER.info("Metoda iz servisa za pronalazenje jednog korisnika"); 
		return userRepository.getOne(id);
	}

	public void remove(Integer id) {
		LOGGER.info("Metoda iz servisa za uklanjanje korisnika"); 
		 userRepository.deleteById(id);
	}
	
	
}
