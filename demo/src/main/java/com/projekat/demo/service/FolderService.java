package com.projekat.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.repository.FolderRepository;

@Service
public class FolderService {

	@Autowired
	private FolderRepository folderRepository;
	
	public Folder findInbox(Account account) {
		return folderRepository.findInboxByAccount("Inbox", account);
	}

}
