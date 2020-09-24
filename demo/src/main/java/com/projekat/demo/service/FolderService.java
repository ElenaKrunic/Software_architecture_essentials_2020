package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.repository.FolderRepository;

@Service
public class FolderService implements FolderServiceInterface {

	@Autowired
	private FolderRepository folderRepository;
	
	//public Folder findInbox(Account account) {
		//return folderRepository.findInboxByAccount("Inbox", account);
	//}
	
	//@Override
	//public Folder findInbox(String name, Account account) {
		//// TODO Auto-generated method stub
		//return folderRepository.findInboxByAccount(name, account);
//	}
	
	//@Override
	//public Folder findOne(Integer id, Account account) {
		//return folderRepository.findByIdAndAccount(id, account);
	//}

	@Override
	public Folder findDraft(Account account) {
		return folderRepository.findByNameAndAccount("Drafts", account);
	}

	@Override
	public Folder findOutbox(Account account) {
		return folderRepository.findByNameAndAccount("Outbox", account);
	}

	@Override
	public List<Folder> findAll(Account account) {
		return folderRepository.findAllByAccount(account);
	}

	@Override
	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public void remove(Integer id) {
		folderRepository.deleteById(id);
		
	}
	
	public List<Folder> findRootFolders(Account account) {
		return folderRepository.findAllByAccountAndParentFolderIsNull(account);
	}



	

}
