package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.dto.RuleDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.repository.AccountRepository;
import com.projekat.demo.repository.FolderRepository;
import com.projekat.demo.repository.MMessageRepository;
import com.projekat.demo.service.AccountServiceInterface;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.FolderServiceInterface;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.RuleService;
import com.projekat.demo.service.UserService;

/**
 *  Kontroler pomocu kojeg vrsimo CRUD nad folderom 
 * @author ElenaKrunic 
 *
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping(value="api/folders")
public class FolderController {
	
    private static final Logger LOGGER = LogManager.getLogger(FolderController.class);

	@Autowired
	private FolderService folderService; 
	
	@Autowired 
	private FolderRepository folderRepository; 
	
	@Autowired
	private AccountServiceInterface accountService; 
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private MailAPI mailApi; 
	
	/**
	 * 
	 * @param id foldera
	 * @return pojedinacan folder iz baze 
	 */
	@GetMapping(value="/{id}")
	public ResponseEntity<FolderDTO> getFolder(@PathVariable("id") Integer id) {
		Folder folder = folderService.findById(id); 
		
		if(folder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND); 
 		}
		
		LOGGER.info("Uspjesno pronadjen folder"); 

		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
	}
	/**
	 * 
	 * @param id foldera
	 * @return lista foldera za nalog 
	 */
	@GetMapping(value="/{id}/folders")
	public ResponseEntity<List<FolderDTO>> getFoldersByAccount(@PathVariable("id") Integer id) {
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<List<FolderDTO>>(HttpStatus.BAD_REQUEST);
		} else {
			List<Folder> folderEntites = this.folderService.findAllByAccount(account);
			List<FolderDTO> dtoFolders = new ArrayList<FolderDTO>();
			
			for(Folder folder : folderEntites) {
				FolderDTO dto = new FolderDTO(folder); 
				dtoFolders.add(dto);
			}
			
			LOGGER.info("Uspjesno vracena lista foldera za odredjen nalog"); 

			return new ResponseEntity<List<FolderDTO>>(dtoFolders, HttpStatus.OK);
		}
	}

	/**
	 *  
	 * @param id
	 * @return lista poruka za folder 
	 */
	@GetMapping(value="/{id}/messages")
	public ResponseEntity<List<MMessageDTO>> getFolderMessages(@PathVariable("id") Integer id) {
		
		Folder folder = folderService.findById(id);
		
		if(folder == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = messageService.findByFolder(folder); 
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
		
		LOGGER.info("Uspjesno vracena lista poruka za folder"); 

		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param folderDTO folder koji ce se kreirati 
	 * @param id naloga kojem ce biti dodijeljen folder 
	 * @return novokreirani folder u bazi podataka 
	 */
	@PostMapping(value="addFolder/{id}",consumes="application/json")
	public ResponseEntity<FolderDTO> addFolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") Integer id) {
		
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND);
		}
		
		Folder folder = new Folder(); 
		folder.setName(folderDTO.getName());
		folder.setParentFolder(null);
		account.addFolder(folder);
		
		folder = folderService.save(folder); 
		
		LOGGER.info("Uspjesno odradjeno dodavanje novog foldera"); 

		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.CREATED);
	}
	 
	
	/**
	 * 
	 * @param folderDTO koji cemo da mijenjamo 
	 * @param id foldera
	 * @return izmijenjeni folder koji cuvamo u bazu 
	 */
	@PutMapping(value="updateFolder/{id}", consumes="application/json")
	public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") Integer id) {
		
		Folder folder = folderService.findById(id); 
		
		if(folder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND); 
		}
		
		folder.setName(folderDTO.getName());
		folder.setParentFolder(null);
		
		folder = this.folderRepository.save(folder);
		
		LOGGER.info("Uspjesno odradjena izmjena foldera"); 
		
		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK); 
	}
	
	/**
	 *  
	 * @param id foldera koji se brise 
	 * @return obrisan entitet folder 
	 */
	
	@DeleteMapping("/deleteFolder/{folderId}")
	public ResponseEntity<Void> deleteFolder(@PathVariable("folderId") Integer id) {
		Folder folder = folderService.findById(id); 
		
		if(folder != null) {
			folderService.remove(folder);
			LOGGER.info("Uspjesno obrisan folder"); 
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); 

		}
	}	

	/**
	 * 
	 * @param id podfoldera 
	 * @return podfolder iz baze 
	 */
	@GetMapping(value="/{id}/subfolders")
	public ResponseEntity<List<FolderDTO>> getSubfolders(@PathVariable("id") Integer id) {
		
		Folder parentFolder = folderService.findById(id); 
		
		if(parentFolder == null) {
			return new ResponseEntity<List<FolderDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<Folder> folders = folderService.findByParent(parentFolder);
		List<FolderDTO> dtoFolders = new ArrayList<FolderDTO>(); 
		
		for(Folder folder : folders) {
			dtoFolders.add(new FolderDTO(folder));
		}
		
		LOGGER.info("Uspjesno vracena lista podfoldera"); 

		return new ResponseEntity<List<FolderDTO>>(dtoFolders, HttpStatus.OK);
	}

	/**
	 * 
	 * @param folderDTO podfolder koji ce se kreirati 
	 * @param id foldera kojem ce biti dodijeljem podfolder 
	 * @return novi podfolder u bazi 
	 */
	@PostMapping(value="/{id}/addSubfolder", consumes="application/json")
	public ResponseEntity<FolderDTO> addSubfolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") Integer id) {
		
		Folder parentFolder = folderService.findById(id); 
		
		if(parentFolder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND); 
		}
		
		Folder folder = new Folder(); 
		folder.setName(folderDTO.getName());
		parentFolder.addSubFolder(folder);
		parentFolder.getAccount().addFolder(folder);
		
		folder = folderService.save(folder);
		
		LOGGER.info("Uspjesno dodavanje novog podfoldera"); 

		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.CREATED);   
	}
}
