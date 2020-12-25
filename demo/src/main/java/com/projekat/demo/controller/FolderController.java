package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.AccountRepository;
import com.projekat.demo.repository.FolderRepository;
import com.projekat.demo.repository.MMessageRepository;
import com.projekat.demo.service.AccountServiceInterface;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.FolderServiceInterface;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.UserService;

/**
 *  Kontroler pomocu kojeg vrsimo CRUD nad folderom 
 * @author ElenaKrunic 
 *
 */
@RestController
@RequestMapping(value="api/folders")
public class FolderController {

	@Autowired
	private FolderService folderService; 
	
	@Autowired 
	private FolderRepository folderRepository; 
	
	@Autowired
	private AccountServiceInterface accountService; 
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private MessageService messageService;
	
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
			
			return new ResponseEntity<List<FolderDTO>>(dtoFolders, HttpStatus.OK);
		}
	}

	/**
	 *  
	 * @param id
	 * @return lista poruka za folder 
	 */
	@GetMapping(value="/{id}/messages")
	public ResponseEntity<List<MMessageDTO>> getFolderMessages(@PathVariable("id") int id) {
		
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()); 
		
		if(user == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.UNAUTHORIZED); 
		}
		
		Folder folder = folderService.findOne(id); 
		
		if(folder == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = messageService.findByFolder(folder); 
		
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>(); 
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
			
		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param newFolder novokreirani folder
	 * @param parent_folder_id folder koji ce biti parent folder za folder koji kreiram 
	 * @param account_id nalog za koji se kreira folder 
	 * @return kreiran folder
	 */
	 @RequestMapping(method = RequestMethod.POST,value="/addNewFolder/{parentFolderId}/{accountId}")
	 public ResponseEntity<FolderDTO> addFolder(@RequestBody FolderDTO newFolder, @PathVariable("parentFolderId") int parent_folder_id, @PathVariable("accountId") int account_id) {
		
		 Account account = this.accountRepository.findById(account_id);
		 if(account == null) {
			 System.out.println("ne postoji ovaj account");
			 //System.out.println(existFolder.getName());
			 return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST);
		 }
		 newFolder.setAccount(account);
		 
		 Folder parentFolder = null;
		 if(parent_folder_id != 0) {
			 parentFolder = this.folderRepository.findById(parent_folder_id);
			 if(parentFolder == null) {
				 System.out.println("ne postoji ovaj parent folder");
				 return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST);
			 }
		 }
		
		 newFolder.setParentFolder(parentFolder);
		 		
		 Folder folder = this.folderService.saveFolderDto(newFolder);
		 return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.CREATED);	
	}
 	
	/**
	 * Metoda za izmjenu postojeceg foldera 
	 * @param folderDTO dto foldera nad kojim se vrsi izmjena 
	 * @param id 
	 * @return izmijenjen folder 
	 */
	@PutMapping(value="/updateFolder/{id}", consumes="application/json")
	public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") int id) {
		Folder folder = folderService.findOne(id); 
		
		if(folder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		folder.setName(folderDTO.getName());
		
		folder = folderService.saveFolderDto(folderDTO); 
		
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
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); 

		}
	}	


}
