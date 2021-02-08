package com.projekat.demo.controller;

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
	@PostMapping("/saveContact/{userId}")
	public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO, @PathVariable("userId") Integer id) {
		
		User user = userService.findOne(id);
		 
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
	
}
