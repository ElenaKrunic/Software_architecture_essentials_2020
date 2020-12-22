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
import com.projekat.demo.entity.Photo;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.ContactRepository;
import com.projekat.demo.repository.PhotoRepository;
import com.projekat.demo.service.ContactServiceInterface;
import com.projekat.demo.service.UserServiceInterface;

@RestController
@RequestMapping(value="api/contacts")
public class ContactController {
	
	@Autowired
	private ContactServiceInterface contactService;
	
	@Autowired
	private ContactRepository contactRepository; 
	
	@Autowired UserServiceInterface userService;
	
	@Autowired PhotoRepository photoRepository;
	
	@GetMapping(value="/getContactsForUser/{userId}")
	public List<Contact> getContacts(@PathVariable("userId") Integer id) { 
		List<Contact> contacts = contactRepository.findAllByUserId(id); 
		return contacts; 
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Integer id) {
		Contact contact = contactService.findOne(id); 
		
		if(contact == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
	} 
	
	@PostMapping("/saveContact/{userId}")
	public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO, @PathVariable("userId") Integer id) {
		
		User user = userService.findOne(id);
		 
		if(user == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		contactDTO.setUser(user); 
		
		Contact contact = this.contactService.addNewContact(contactDTO);
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.CREATED); 
	}
	
 
	@PutMapping(value="/updateContact/{id}", consumes="application/json")
	public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO, @PathVariable("id") Integer id) {
		
		Contact contact = contactService.findOne(id);
		
		if(contact==null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST);
		}
		
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setDisplayName(contactDTO.getDisplayName());
		contact.setEmail(contactDTO.getEmail());
		contact.setNote(contactDTO.getNote());
		//if(contact.getPhoto() != null) {
			//contact.getPhoto().setPath(contactDTO.getPhoto().getPath());
		//} else {
			//contact.getPhoto().setPath(null);
			//System.out.println("Kontakt nema sliku, postaviti da bude nullable=false za sliku"); 
	//	}
		//contact.getPhoto().setPath(contactDTO.getPhoto().getPath());
		//contact = this.contactService.save(contact);
		contact = this.contactService.save(contactDTO);
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK); 
	} 
	
	
	@DeleteMapping(value="deleteContact/{id}")
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
