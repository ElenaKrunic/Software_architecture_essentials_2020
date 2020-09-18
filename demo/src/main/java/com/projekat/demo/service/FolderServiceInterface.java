package com.projekat.demo.service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderServiceInterface {
	
	public Folder findInbox(Account account);

}
