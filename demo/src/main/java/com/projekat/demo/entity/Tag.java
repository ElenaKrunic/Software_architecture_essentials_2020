package com.projekat.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tags")
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Column(name="name",unique=false, nullable=false)
	private String name; 
	
	//veze tagova : many to many sa mess
	//many to one sa userom 
	//
	
	@ManyToMany(cascade= {CascadeType.ALL}, fetch = FetchType.LAZY,mappedBy="tags")
	private List<MMessage> messages;
	
	@ManyToOne
	@JoinColumn(name="user", referencedColumnName = "id", nullable=false)
	private User user;

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

	public List<MMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<MMessage> messages) {
		this.messages = messages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tag(Integer id, String name, List<MMessage> messages, User user) {
		super();
		this.id = id;
		this.name = name;
		this.messages = messages;
		this.user = user;
	}

	public Tag() {
		super();
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", messages=" + messages + ", user=" + user + "]";
	} 
	
	
	
}
