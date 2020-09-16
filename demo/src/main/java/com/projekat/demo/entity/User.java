package com.projekat.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Column(name="username", unique=true, nullable=false)
	private String username; 
	
	@Column(name="password", unique=true, nullable=false)
	private String password; 
	
	@Column(name="firstname", unique=false, nullable=false)
	private String firstname; 
	
	@Column(name="lastname", unique=false, nullable=true)
	private String lastname; 
	
	//veza one to many : user i account 
	//one to many : user i tag 
	//one to many user i contact 
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="user")
	private List<Contact> contacts; 
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="user")
	private List<Tag> tags;
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="user")
	private List<Account> accounts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public User(Integer id, String username, String password, String firstname, String lastname, List<Contact> contacts,
			List<Tag> tags, List<Account> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.contacts = contacts;
		this.tags = tags;
		this.accounts = accounts;
	}

	public User() {
		super();
	} 
	
}
