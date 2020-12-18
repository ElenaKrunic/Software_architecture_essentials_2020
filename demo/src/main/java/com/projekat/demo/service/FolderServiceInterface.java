package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderServiceInterface {
	
	//folder po id 
	public Folder findOne(Integer id); 
	
	public Folder findInbox(Account account);
		
	public Folder findDraft(Account account);
	
	public Folder findOutbox(Account account);
	
	//public List<Folder> findAll(Account account);
	//pronaci sve foldere 
	public List<Folder> findAll();
	
	public Folder save(Folder folder);
	
	public void remove(Integer id);


}
