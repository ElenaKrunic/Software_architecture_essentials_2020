package com.projekat.demo.entity;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
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

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="folders")
public class Folder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
		
	@Column(name = "name", nullable = false)
	private String name;
		
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "parent_folder", referencedColumnName = "id", nullable = true)
	private Folder parentFolder;

		
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "parentFolder")
	@JsonIgnore
	private Set<Folder> subFolders = new HashSet<Folder>();
		
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "account", referencedColumnName = "id", nullable = true)
	private Account account;
		
	@JsonIgnore
	@OneToMany(cascade = { ALL }, fetch = FetchType.LAZY, mappedBy = "folder")
	 private Set<MMessage> messages = new HashSet<MMessage>();
		
	@JsonIgnore
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "sourceFolder", orphanRemoval = true)
	private Set<Rule> rules = new HashSet<Rule>();

	public Folder() {
			
	}
	
	public void addMessage(MMessage message) {
		if(message.getFolder() != null) {
			message.getFolder().removeMessage(message);
			message.setFolder(this);
			getMessages().add(message);
		}
	}

	public void removeMessage(MMessage message) {
		message.setFolder(null);
		getMessages().remove(message);
	}	
	
	/*
	public void addRule(Rule rule) {
		if(rule.getFolder()!= null) 
			rule.getFolder().removeRule(rule);
		rule.setFolder(this);
		getRules().add(rule);
	}		
	
	public void removeRule(Rule rule) {
		rule.setFolder(null);
		getRules().add(rule);
	}
	*/
	
	public void addRuleToDestinationFolder(Rule rule) {
		if(rule.getDestinationFolder() != null)
			rule.getDestinationFolder().removeRuleFromDestinationFolder(rule);
		rule.setDestinationFolder(this);
		getRules().add(rule);
	}
	
	public void removeRuleFromDestinationFolder(Rule rule) {
		rule.setDestinationFolder(null);
		getRules().add(rule);
	}
	
	public void addRuleToSourceFolder(Rule rule) {
		if(rule.getSourceFolder() != null) 
			rule.getSourceFolder().removeRuleFromSourceFolder(rule);
		rule.setSourceFolder(this);
		getRules().add(rule);
	}
	
	public void removeRuleFromSourceFolder(Rule rule) {
		rule.setSourceFolder(null);
		getRules().add(rule);
	}
	
	public void addSubFolder(Folder folder) {
		if(folder.getParentFolder() != null) 
			folder.getParentFolder().removeSubFolder(folder);
		folder.setParentFolder(this);
		getSubFolders().add(folder);
	}
	
	public void removeSubFolder(Folder folder) {
		folder.setParentFolder(null);
		getSubFolders().remove(folder);
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
		
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}
		
	public Set<MMessage> getMessages() {
		return messages;
	}
	
	public void setMessages(Set<MMessage> messages) {
		this.messages = messages;
	}

	/*
	public void fixRules() {
		for (Rule rule : rules) {
			rule.setFolder(this);
		}
	}
	*/

	public Set<Folder> getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(Set<Folder> subFolders) {
		this.subFolders = subFolders;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", account=" + account + ", subFolders=" + subFolders
					 + ", rules=" + rules + "]";
	}

}
