package com.projekat.demo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="contacts")
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3339913746382517243L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "first_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String lastName;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = true)
	private String displayName;

	@Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = true)
	private String email;

	@Column(name = "note", columnDefinition = "TEXT", nullable = true)
	private String note;
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JsonManagedReference
    @JoinColumn(name = "photo_id", referencedColumnName = "photo_id",nullable = true)
	private Photo photo;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = true)
	private User user;

	public Contact() {}
	
	
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
