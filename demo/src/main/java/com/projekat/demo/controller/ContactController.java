package com.projekat.demo.controller;

import java.security.Principal;
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
import com.projekat.demo.dto.UserDTO;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.ContactRepository;
import com.projekat.demo.repository.PhotoRepository;
import com.projekat.demo.service.ContactServiceInterface;
import com.projekat.demo.service.UserServiceInterface;

/**
 * Implementiran kontroler za rad sa kontaktima 
 * @author Elena Krunic 
 *
 */
@RestController
@RequestMapping(value="api/contacts")
public class ContactController {
	
	@Autowired
	private ContactServiceInterface contactService;
	
	@Autowired
	private ContactRepository contactRepository; 
	
	@Autowired UserServiceInterface userService;
	
	@Autowired PhotoRepository photoRepository;
	
	@GetMapping
	public ResponseEntity<List<ContactDTO>> getAllContacts() {
		List<Contact> contacts = contactService.findAll(); 
		
		//System.out.println("Kontakti su " + contacts);
		//konvertovanje u beanove dto 
		List<ContactDTO> dtoContacts = new ArrayList<ContactDTO>(); 
		
		for(Contact contact : contacts) {
			dtoContacts.add(new ContactDTO(contact));
		}
		return new ResponseEntity<List<ContactDTO>>(dtoContacts, HttpStatus.OK); 
	}
	
	/**
	 * 
	 * @param id korisnika cije kontakte zelimo prikazati 
	 * @return lista kontakata za korisnika 
	 */
	@GetMapping("/getContactsForUser")
	public List<Contact> getContacts() { 
		List<Contact> contacts = contactRepository.findAllByUserId(UserController.korisnikID); 
		return contacts; 
	}

	/**
	 * 
	 * @param id kontakta 
	 * @return pojedinacan kontakt 
	 */
	@GetMapping(value="/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Integer id) {
		Contact contact = contactService.findOne(id); 
		
		if(contact == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
	} 
	
	
	/**
	 * 
	 * @param contactDTO dto bean koji zelimo da kreiramo 
	 * @param id korisnika kojem ce biti dodijeljen kontakt 
	 * @return novokreirani kontakt u bazi 
	 */
	@PostMapping(value= "/saveContact",consumes="application/json")
	public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO) {
		
		User user = userService.findOne(UserController.korisnikID);
		 
		if(user == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		//Photo photo = new Photo(); 
		//photo.setPath(contactDTO.getPhoto().getPath());
		//photo = this.photoRepository.save(photo); 
		
		Contact contact = new Contact(); 
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setDisplayName(contactDTO.getDisplayName());
		contact.setEmail(contactDTO.getEmail());
		contact.setNote(contactDTO.getNote());
		//contact.setPhoto(photo);
		if(contact.getPhoto() ==  null) {
			contact.setPhoto(contactDTO.getPhoto());
		} else {
			contact.getPhoto().setPath(contactDTO.getPhoto().getPath());
		}		
		user.addContact(contact); 
		
		contact = this.contactService.save(contact);
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK); 
	}
	
 
	/**
	 * 
	 * @param contactDTO bean koji zelimo da izmijenimo 
	 * @param id kontakta koji mijenjamo 
	 * @return izmijenjen postojeci kontakt u bazi podataka 
	 */
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
		if(contact.getPhoto() ==  null) {
			contact.setPhoto(contactDTO.getPhoto());
		} else {
			contact.getPhoto().setPath(contactDTO.getPhoto().getPath());
		}		
		contact = this.contactService.save(contact);
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK); 
		
	} 
	
	/**
	 * 
	 * @param id kontakta koji zelimo obrisati 
	 * @return obrisan kontakt 
	 */
	@DeleteMapping(value="deleteContact/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable("id") Integer id) {
		Contact contact = contactService.findOne(id);

		if(contact!=null) {
			contactService.removeContact(contact); 
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); 
		}
	
	} 	
	
	
	//===========================================
	
	
	@PostMapping("/saveContactForUser")
	public ResponseEntity<ContactDTO> saveContactToUser(@RequestBody ContactDTO contactDTO, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		 
		if(user == null) {
			return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		Photo photo = new Photo(); 
		photo.setPath(contactDTO.getPhoto().getPath());
		photo = this.photoRepository.save(photo); 
		
		Contact contact = new Contact(); 
		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setDisplayName(contactDTO.getDisplayName());
		contact.setEmail(contactDTO.getEmail());
		contact.setNote(contactDTO.getNote());
		contact.setPhoto(photo);
		user.addContact(contact); 
		
		contact = this.contactService.save(contact);
		
		return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK); 
	}
}
