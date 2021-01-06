package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.repository.FolderRepository;

@Service
public class FolderService implements FolderServiceInterface {

	@Autowired
	private FolderRepository folderRepository;

	public List<Folder> findAllByAccount(Account account) {
		return folderRepository.findAllByAccount(account);
	}

	@Override
	public Folder saveFolderDto(FolderDTO folderDTO) {
		Folder folder = new Folder(); 
		folder.setName(folderDTO.getName());
		//folder.setParentFolder(folderDTO.getParentFolder());
		//folder.setAccount(folderDTO.getAccount());
		
		folder = folderRepository.save(folder); 
		return folder; 
	}

	
	public Folder save(Folder folder) {
		return folderRepository.save(folder); 
	}

	@Override
	public Folder findById(Integer folderId) {
		return folderRepository.findById(folderId);
	}

	@Override
	public Folder findOne(Integer id) {
		return folderRepository.findById(id);
	}

	@Override
	public void remove(Folder folder) {
		folderRepository.delete(folder);
	}

	@Override
	public Folder findInbox(Account account) {
		return folderRepository.findByNameAndAccount("Inbox", account);
	}

	@Override
	public List<Folder> findByParent(Folder parentFolder) {
		return folderRepository.findByParentFolder(parentFolder);
	}


}
