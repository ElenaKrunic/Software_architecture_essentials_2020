package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;

public class TagDTO {

	private Integer id; 
	private String name; 
	private User user; 
	//veze izmedju beanova 
	private Set<MMessage> messages;
	
	
	public Set<MMessage> getMessages() {
		return messages;
	}
	public void setMessages(Set<MMessage> messages) {
		this.messages = messages;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public TagDTO(Integer id, String name, Set<MMessage> messages, User user) {
		super();
		this.id = id;
		this.name = name;
		this.messages = messages; 
		this.user = user;
	}
	public TagDTO() {
	}
	
	//transformacija entiteta u dtobean: 
	public TagDTO(Tag tag){
		this.id=tag.getId();
		this.name=tag.getName();
		this.messages = tag.getMessages();
		this.user = tag.getUser();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
