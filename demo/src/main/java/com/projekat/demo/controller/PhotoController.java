
package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projekat.demo.dto.PhotoDTO;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;
import com.projekat.demo.service.ContactService;
import com.projekat.demo.service.PhotoService;

@RestController
@RequestMapping("contacts/{id}/photos")
public class PhotoController {

	private PhotoService photoService;
	
	private ContactService contactService; 
	
	@GetMapping
	public ResponseEntity<?> getAllPhotos(@PathVariable("id") Integer contactId) {
		if(contactId < 0) {
			return new ResponseEntity<>("Kontakt mora biti obiljezen ID-jem!", HttpStatus.BAD_REQUEST);
		}
		
		Contact contact = contactService.findOne(contactId);
		 
		List<PhotoDTO> photos = new ArrayList<PhotoDTO>(); 
		for(Photo photo: photoService.getPhotos(contact)) {
			photos.add(new PhotoDTO(photo));
		}	
		return new ResponseEntity<List<PhotoDTO>>(photos, HttpStatus.OK);
	}
	
	@PostMapping("/{photo_id}")
	public ResponseEntity<?> dodajFotografiju(@PathVariable("id") Integer contactId, @PathVariable("photo_id") Integer photoId, @RequestParam("upload_photo") MultipartFile photoToUpload) {
		
		Contact contact = contactService.findOne(contactId); 
		if(contact == null) {
			new ResponseEntity<>("Kontakt ne postoji!", HttpStatus.NOT_FOUND);
		}
		
		Photo photo = photoService.getOne(photoId); 
		
		try {
			byte[] b = photoToUpload.getBytes();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
}
		


	
