package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.repository.MMessageRepository;

@Service
public class MessageService implements MessageServiceInterface {
	/*
	@Autowired
	MMessageRepository messageRepository;
	
	@Autowired
	private FolderService folderService; 

	//metoda koja ce da ucita sve poruke za odredjeni nalog 
	public List<MMessage> findAllMessagesByAccount(Account account) {
		//pronadji folder korisnika 
		Folder folder = folderService.findInbox(account);
		
		return messageRepository.findAllMessagesByAccount(account,folder);
	}
*/
}
