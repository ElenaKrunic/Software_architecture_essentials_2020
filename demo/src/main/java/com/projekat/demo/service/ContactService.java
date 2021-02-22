package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.repository.ContactRepository;

@Service
public class ContactService implements ContactServiceInterface {

    private static final Logger LOGGER = LogManager.getLogger(ContactService.class);

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> findAll() {
		LOGGER.info("Metoda iz servisa za pronalazenje svih kontakata"); 
		return contactRepository.findAll();
	}

	public Contact save(Contact contact) {
		LOGGER.info("Metoda iz servisa za cuvanje kontakta"); 
		return contactRepository.save(contact); 
	}

	@Override
	public Contact findOne(Integer contactId) {
		LOGGER.info("Metoda iz servisa za pronalazenje kontakta"); 
		return contactRepository.getOne(contactId);
	}

	@Override
	public Contact addNewContact(ContactDTO contactDTO) {
		Contact contact = new Contact(); 
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setDisplayName(contactDTO.getDisplayName());
		contact.setEmail(contactDTO.getEmail());
		contact.setNote(contactDTO.getNote());
		//contact.setPhotoPath(contactDTO.getPhotoPath());
		//contact.setPhoto(contactDTO.getPhoto());
		//contact.setUser(contactDTO.getUser());
		
		contact = this.contactRepository.save(contact);
		
		LOGGER.info("Metoda iz servisa za dodavanje novog kontakta"); 

		return contact; 
	}

	@Override
	public void removeContact(Contact contact) {
		
		LOGGER.info("Metoda iz servisa za brisanje kontakta"); 
		
		contactRepository.delete(contact);
		
	}
	
}
