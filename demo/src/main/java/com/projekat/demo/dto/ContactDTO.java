package com.projekat.demo.dto;

import java.util.List;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;

public class ContactDTO {
	//atributi se isto zovu u Contact i u DTO 
	private Integer id; 
	private String firstname; 
	private String lastname; 
	private String displayname; 	
	private String email; 
	private String note; 
	private List<Photo> photos;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
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
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	@Override
	public String toString() {
		return "ContactDTO [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", displayname="
				+ displayname + ", email=" + email + ", note=" + note + ", photos=" + photos + "]";
	} 
	
	
}
