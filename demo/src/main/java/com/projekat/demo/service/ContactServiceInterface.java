package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Contact;

public interface ContactServiceInterface {

	List<Contact> findAll(); 
	
	Contact findOne(Integer contactId);
	
	Contact save(Contact contact); 
	
	void removeContact(Integer id);

}
