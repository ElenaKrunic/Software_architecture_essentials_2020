package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;

public class ContactDTO {
	//atributi se isto zovu u Contact i u DTO 
	private Integer id; 
	private String firstName; 
	private String lastName; 
	private String displayName; 	
	private String email; 
	private String note; 
	
	//veze izmedju beanova 
	private List<PhotoDTO> photos = new ArrayList<PhotoDTO>();
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ContactDTO(Contact contact) {
		this.id = contact.getId();
		this.firstName=contact.getFirstName(); 
		this.lastName = contact.getLastName(); 
		this.displayName = contact.getDisplayName(); 
		this.email = contact.getEmail(); 
		this.note = contact.getNote(); 
		
		//for(Photo photo : contact.getPhotos()) {
			//this.photos.add(new PhotoDTO(photo));
			//photos.add(new PhotoDTO(photo));
		//}
	} 
	
	public ContactDTO(Integer id, String firstname, String lastname, String displayname, String email, String note,
			byte[] image) {
		super();
		this.id = id;
		this.firstName = firstname;
		this.lastName = lastname;
		this.displayName = displayname;
		this.email = email;
		this.note = note;
	} 
	
	public ContactDTO() {
		
	}
	
	
	/*
	public ContactDTO(Integer id, String firstname, String lastname, String displayname, String email, String note,
			List<Photo> photos) {
		super();
		this.id = id;
		this.firstName = firstname;
		this.lastName = lastname;
		this.displayName = displayname;
		this.email = email;
		this.note = note;
		this.photos = photos;
	} */
	
	
	
	public ContactDTO(String email) {
		super();
		this.email= email;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<PhotoDTO> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoDTO> photos) {
		this.photos = photos;
	}
	
	
	
}
