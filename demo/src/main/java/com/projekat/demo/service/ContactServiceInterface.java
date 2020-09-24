package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Contact;

public interface ContactServiceInterface {

	List<Contact> findAll(); 
	
	
	Contact saveContact(Contact contact); 

}
