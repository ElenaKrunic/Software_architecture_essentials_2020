package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;

public class FolderDTO {
	
	private Integer id; 
	private String name;
	//veze izmedju beanova 
	private Folder parentFolder;
	private Set<Folder> subFolders;
    private Set<Rule> rules;
    private Set<MMessage> messages;
	private Account account;
	
	public FolderDTO(Integer id, String name, Folder parentFolder, Set<Folder> subFolders, Set<Rule> rules,
			Set<MMessage> messages, Account account) {
		super();
		this.id = id;
		this.name = name;
		this.parentFolder = parentFolder;
		this.subFolders = subFolders;
		this.rules = rules;
		this.messages = messages;
		this.account = account;
	}
	
	public FolderDTO(Folder folder) {
		this(folder.getId(), folder.getName(), folder.getParentFolder(), folder.getSubFolders(), folder.getRules(), folder.getMessages(),folder.getAccount());
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
	public Folder getParentFolder() {
		return parentFolder;
	}
	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}
	public Set<Folder> getSubFolders() {
		return subFolders;
	}
	public void setSubFolders(Set<Folder> subFolders) {
		this.subFolders = subFolders;
	}
	public Set<Rule> getRules() {
		return rules;
	}
	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}
	public Set<MMessage> getMessages() {
		return messages;
	}
	public void setMessages(Set<MMessage> messages) {
		this.messages = messages;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	

	
}
