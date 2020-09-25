package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.repository.ContactRepository;

@Service
public class ContactService implements ContactServiceInterface {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> findAll() {
		//return contactRepository.findAll();
		//return contactRepository.findAll();
		return contactRepository.findAll();
	}

	@Override
	public Contact saveContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public Contact findOne(Integer contactId) {
		return contactRepository.getOne(contactId);
	}

	@Override
	public void removeContact(Integer id) {
		contactRepository.deleteById(id);
		
	}
	
	
	
}
