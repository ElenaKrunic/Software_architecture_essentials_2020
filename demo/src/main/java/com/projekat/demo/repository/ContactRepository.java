package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.User;


public interface ContactRepository extends JpaRepository<Contact, Integer> {

	public List<Contact> findAllByUser(User user);
	
	public Contact findByIdAndUser(Integer id, User user);
	
	public List<Contact> findAllByUserId(Integer id); 
	
	
	
}
