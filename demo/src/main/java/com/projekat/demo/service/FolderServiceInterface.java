package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderServiceInterface {
	
	
	public Folder findOne(Integer id); 
		
	public Folder findInbox(Account account);
	
	public Folder save(Folder folder);
	
	public void remove(Folder folder);

	Folder saveFolderDto(FolderDTO folderDTO);
	
	Folder findById(Integer folderId);

}
