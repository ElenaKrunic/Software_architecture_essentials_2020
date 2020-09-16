package com.projekat.demo.entity;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="folders")
public class Folder {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Column(name="name", unique=true, nullable=false)
	private String name; 
	
	//veze 
	//folder ima parent folder 
	//one to many sa rule 
	//many to one sa account 
	//one to many sa messages 
	
	@ManyToOne
	@JoinColumn(name="account", referencedColumnName = "id", nullable=false)
	private Account account; 
	
	@ManyToOne
	@JoinColumn(name="parentFolder", referencedColumnName = "id", nullable=true)
	private Folder parentFolder; 
	
	//postaviti vezu sa rule 
	@OneToMany(cascade= {CascadeType.REMOVE, PERSIST} , fetch= FetchType.LAZY, mappedBy = "folder")
	private List<Rule> rules;
	
	@OneToMany(cascade= {CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="folder")
	private List<MMessage> messages;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public List<MMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<MMessage> messages) {
		this.messages = messages;
	}

	public Folder(Integer id, String name, Account account, Folder parentFolder, List<MMessage> messages) {
		super();
		this.id = id;
		this.name = name;
		this.account = account;
		this.parentFolder = parentFolder;
		this.messages = messages;
	}

	public Folder() {
		super();
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", account=" + account + ", parentFolder=" + parentFolder
				+ ", messages=" + messages + "]";
	} 
}
