package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.repository.ContactRepository;

@Service
public class ContactService implements ContactServiceInterface {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}

	public Contact save(ContactDTO newContact) {
		Contact c = new Contact();
		c.setFirstName(newContact.getFirstName());
		c.setLastName(newContact.getLastName());
		c.setDisplayName(newContact.getDisplayName());
		c.setEmail(newContact.getEmail());
		c.setNote(newContact.getNote());
		c.setPhoto(newContact.getPhoto());
		c.setUser(newContact.getUser());
		
		c = this.contactRepository.save(c);
		return c;
	}

	@Override
	public Contact findOne(Integer contactId) {
		return contactRepository.getOne(contactId);
	}

	@Override
	public void removeContact(Integer id) {
		contactRepository.deleteById(id);
		
	}

	@Override
	public Contact addNewContact(ContactDTO contactDTO) {
		Contact contact = new Contact(); 
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setDisplayName(contactDTO.getDisplayName());
		contact.setEmail(contactDTO.getEmail());
		contact.setNote(contactDTO.getNote());
		contact.setPhoto(contactDTO.getPhoto());
		contact.setUser(contactDTO.getUser());
		
		contact = this.contactRepository.save(contact);
		
		return contact; 
	}
	
}
