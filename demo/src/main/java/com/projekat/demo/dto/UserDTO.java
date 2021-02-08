package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;

public class UserDTO {
	
	private Integer id; 
	private String username; 
	private String password; 
	private String firstName; 
	private String lastName;
	
	//veze izmedju beanova
	private List<AccountDTO> accounts = new ArrayList<AccountDTO>();
	private List<ContactDTO> contacts = new ArrayList<ContactDTO>();
	private List<TagDTO> tags = new ArrayList<TagDTO>();
		
	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}

	public UserDTO() {
		
	}
	
	public UserDTO(User user) {
		this.id = user.getId(); 
		this.username = user.getUsername(); 
		this.password = user.getPassword(); 
		this.firstName = user.getFirstName(); 
		this.lastName = user.getLastName();
		for(Account account : user.getAccounts()) {
			accounts.add(new AccountDTO(account));
		}
	}
	
	public List<AccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDTO> accounts) {
		this.accounts = accounts;
	}

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
	
	
}
