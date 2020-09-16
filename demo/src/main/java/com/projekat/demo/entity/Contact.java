package com.projekat.demo.entity;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="contacts")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "first_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String lastName;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String displayName;

	@Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
	private String email;

	@Column(name = "note", columnDefinition = "TEXT", nullable = false)
	private String note;

	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;

	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "contact")
	private List<Photo> photos;
	
	
	public Contact() {
		super();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public List<Photo> getPhotos() {
		return photos;
	}



	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	/*
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Column(name="firstname", unique=false, nullable=false)
	private String firstname; 
	
	@Column(name="lastname", unique=false, nullable=true)
	private String lastname; 
	
	@Column(name="displayname", unique=false, nullable=true)
	private String displayname; 
	
	@Column(name="email", unique=true, nullable=false)
	private String email; 
	
	@Column(name="note",unique=false, nullable=true)
	private String note; 
	
	//veza kontakta prema useru je many to one 
	//veza kontakta prema slici je one to many 
	@OneToMany(cascade= {ALL}, fetch=LAZY, mappedBy="contact")
	private List<Photo> photos;
	
	@ManyToOne
	@JoinColumn(name="user", referencedColumnName="id", nullable=false)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", displayname="
				+ displayname + ", email=" + email + ", note=" + note + ", photos=" + photos + ", user=" + user + "]";
	}

	public Contact(Integer id, String firstname, String lastname, String displayname, String email, String note,
			List<Photo> photos, User user) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.displayname = displayname;
		this.email = email;
		this.note = note;
		this.photos = photos;
		this.user = user;
	}

	public Contact() {
		super();
	}
	*/
	
}
