package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.service.ContactServiceInterface;


/**
 * Kontroler u kome su napisane metode za : 1- zadobijanje svih kontakta iz baze 
 * 2- za pronalazenje pojedinacnog kontakta iz baze na osnovu njegovog ID-a 
 * 3- za snimanje kontakta u bazu 
 * 4- za osvjezavanje kontakta iz baze 
 * 5- za brisanje kontakta iz baze 
 * @author WIN7
 *
 */

@RestController
@RequestMapping(value="api/contacts")
public class ContactController {
	
	@Autowired
	private ContactServiceInterface contactService; 
	
	@GetMapping
	public ResponseEntity<List<ContactDTO>> getContacts() {
		List<Contact> contacts = contactService.findAll(); 
		
		//System.out.println("Kontakti su " + contacts);
		//konvertovanje u beanove dto 
		List<ContactDTO> dtoContacts = new ArrayList<ContactDTO>(); 
		
		for(Contact contact : contacts) {
			dtoContacts.add(new ContactDTO(contact));
		}
		return new ResponseEntity<List<ContactDTO>>(dtoContacts, HttpStatus.OK); 
	}
	
	//pronalak kontakta po ID 
	@GetMapping(value="/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Integer id) {
		Contact contact = contactService.findOne(id); 
		
		if(contact == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
	} 
	
	//cuvanje kontakta u bazu 
	@PostMapping(consumes="application/json")
	public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO) {
		
		Contact contact = new Contact();
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		//za pocetak samo first name i last name 
		
		contact = contactService.saveContact(contact); 
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.CREATED);
	}
 
	
	//update contact 
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO, @PathVariable("id") Integer id) {
		
		Contact contact = contactService.findOne(id);
		
		if(contact==null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST);
		}
		
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		
		contact = contactService.saveContact(contact);
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
	
	}
	
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable("id") Integer id) {
		Contact contact = contactService.findOne(id);

		if(contact!=null) {
			contactService.removeContact(id); 
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); 
		}
	
	} 
	
}
