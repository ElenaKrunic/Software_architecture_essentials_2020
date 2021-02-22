package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.repository.FolderRepository;

@Service
public class FolderService implements FolderServiceInterface {

    private static final Logger LOGGER = LogManager.getLogger(FolderService.class);

	@Autowired
	private FolderRepository folderRepository;

	public List<Folder> findAllByAccount(Account account) {
		LOGGER.info("Metoda iz servisa za pronalazenje svih foldera"); 
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
		
		LOGGER.info("Metoda iz servisa za cuvanje foldera"); 
		return folderRepository.save(folder); 
	}

	@Override
	public Folder findById(Integer folderId) {
		LOGGER.info("Metoda iz servisa za pronalazenje foldera po ID-u"); 

		return folderRepository.findById(folderId);
	}

	@Override
	public Folder findOne(Integer id) {
		LOGGER.info("Metoda iz servisa za pronalazenje foldera po ID-u"); 
		return folderRepository.findById(id);
	}

	@Override
	public void remove(Folder folder) {
		folderRepository.delete(folder);
	}

	@Override
	public Folder findInbox(Account account) {
		LOGGER.info("Metoda iz servisa za pronalazenje foldera po nalogu i Inbox folderu"); 
		return folderRepository.findByNameAndAccount("Inbox", account);
	}
	
	@Override
	public Folder findOutbox(Account account) {
		LOGGER.info("Metoda iz servisa za pronalazenje foldera po nalogu i Outbox folderu"); 
		return folderRepository.findByNameAndAccount("Outbox", account);
	}

	@Override
	public List<Folder> findByParent(Folder parentFolder) {
		LOGGER.info("Metoda iz servisa za pronalazenje foldera po parent folderu"); 
		return folderRepository.findByParentFolder(parentFolder);
	}


}
