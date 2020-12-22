package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.entity.Contact;

public interface ContactServiceInterface {

	List<Contact> findAll(); 
	
	Contact findOne(Integer contactId);
	//staro 
	//Contact save(Contact contact); 
	
	Contact save(ContactDTO newContact);
	
	void removeContact(Integer id);

	Contact addNewContact(ContactDTO contactDTO);

	//List<Contact> findAllByUserId(Integer id);

}
