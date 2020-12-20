package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AccountDTO;
import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AccountServiceInterface;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping(value="api/accounts")
public class AccountController {
	
	@Autowired
	private AccountServiceInterface accountServiceInterface; 

	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private MessageService messageService; 
	
	@Autowired
	private FolderService folderService; 
	
	@Autowired
	private UserService userService; 
		
	
	/**
	 * 
	 * @param id korisnika 
	 * @return lista naloga za korisnika 
	 */
	@GetMapping("/getAccountsForUser/{id}")
	public ResponseEntity<List<AccountDTO>> getAllAccountsForUser(@PathVariable("id") Integer id) {
		List<Account> accounts = accountService.findAllByUserId(id);
		List<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>(); 
		if(accounts == null) {
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.BAD_REQUEST);
		}
		for(Account account : accounts) {
			AccountDTO dto = new AccountDTO(account); 
			dtoAccounts.add(dto);
		}
		return new ResponseEntity<List<AccountDTO>>(dtoAccounts, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id korisnika
	 * @param oldUsername staro korisnicko ime 
 	 * @param newUsername novo korisnicko ime 
	 * @return novi korisnicki nalog 
	 */
	@PutMapping("/changeAccount/{id}/{old_username}/{new_username}")
	public ResponseEntity<AccountDTO> changeAccount(@PathVariable("id") Integer id, @PathVariable("old_username") String oldUsername, @PathVariable("new_username") String newUsername) {
		
		Account oldAccount = accountService.findByAccountIdAndUsername(id, oldUsername);
		
		if(oldUsername != null) {
			if(oldAccount == null) {
				System.out.println("Stari nalog je null "); 
				return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST); 
			} 
			
			oldAccount = accountService.save(oldAccount);
		}
		
		Account newAccount = accountService.findByAccountIdAndUsername(id, newUsername);
		
		if(newAccount == null) {
			System.out.println("Nema novog naloga !"); 
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		accountService.save(newAccount);
		System.out.println("Vrijednosti novog naloga su " + newAccount);
	
		return new ResponseEntity<AccountDTO>(new AccountDTO(newAccount), HttpStatus.OK); 
	}
	
	
	/**
	 * 
	 * @param id naloga 
	 * @return lista poruka za nalog 
	 */
	@GetMapping(value="/{id}/messages")
	public ResponseEntity<List<MMessageDTO>> getMessageByAccount(@PathVariable("id") Integer id) {
		Account account = accountService.findOne(id);
		
		if(account == null ) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.BAD_REQUEST);
		} else {
			List<MMessage> messageEntites = messageService.findAllByAccount(account);
			List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
			
			for(MMessage message : messageEntites) {
				MMessageDTO dto = new MMessageDTO(message); 
				dtoMessages.add(dto);
			}
			
			return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
		}
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
			List<Folder> folderEntites = folderService.findAllByAccount(account);
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
	 * @return listu naloga 
	 */
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = accountServiceInterface.findAll();
		
		List<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>(); 
		
		for(Account account : accounts) {
			dtoAccounts.add(new AccountDTO(account));
		}
		return new ResponseEntity<List<AccountDTO>>(dtoAccounts, HttpStatus.OK);  
	}
		
	/**
	 * dodavanje novog naloga 
	 * @param accountDTO
	 * @return
	 */
	@PostMapping("/saveAccount/{username}")
	public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO, @PathVariable("username") String username) {
		
		//--------------------------- VALIDACIJA ----------------------------------------------
		/*
		if (accountDTO.getSmtpAddress().isEmpty())
			return new ResponseEntity<AccountDTO>("Smtp Address can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getSmtpPort() == 0)
			return new ResponseEntity<String>("Smtp Port can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getInServerType() == 0)
			return new ResponseEntity<String>("In Server Type can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getInServerAddress().isEmpty())
			return new ResponseEntity<String>("In Server Address can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getInServerPort() == 0)
			return new ResponseEntity<String>("In Server Port can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getUsername().isEmpty())
			return new ResponseEntity<String>("Username can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getPassword().isEmpty())
			return new ResponseEntity<String>("Password can't be empty!", HttpStatus.BAD_REQUEST);
		else if (accountDTO.getDisplayName().isEmpty())
			return new ResponseEntity<String>("Display Name can't be empty!", HttpStatus.BAD_REQUEST); */
		
		User user = this.userService.findByUsername(username); 
		Account userAccount = this.accountService.findByAccountIdAndUsername(user.getId(), username);
		
		if(user == null || userAccount != null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST); 
		}
	
		Account account = new Account(); 
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		account.setSmtpAddress(accountDTO.getSmtpAddress());
		account.setSmtpPort(accountDTO.getSmtpPort()); 
		account.setInServerType(accountDTO.getInServerType());
		account.setInServerAddress(accountDTO.getInServerAddress());
		account.setInServerPort(accountDTO.getInServerPort());
		account.setUser(user);
		
		List<Folder> defaultFolders = new ArrayList<Folder>(); 
		
		Folder inbox = new Folder(); 
		inbox.setName("Inbox");
		inbox.setAccount(account);
		inbox.setParentFolder(null);
		inbox.setSubFolders(null);
		
		Folder outbox = new Folder(); 
		outbox.setName("Outbox");
		outbox.setAccount(account);
		outbox.setParentFolder(null);
		outbox.setSubFolders(null);
		
		Folder drafts = new Folder(); 
		drafts.setName("Drafts");
		drafts.setAccount(account);
		drafts.setParentFolder(null);
		drafts.setSubFolders(null);
		
		Folder primary = new Folder(); 
		primary.setName("Primary");
		primary.setAccount(account);
		primary.setParentFolder(null);
		primary.setSubFolders(null);
				
		defaultFolders.add(primary);
		defaultFolders.add(outbox);
		defaultFolders.add(drafts);
		defaultFolders.add(inbox); 

		account.setFolders(defaultFolders);
		
		MailAPI mailApi = new MailAPI();
		
		boolean connectAccountToUser = mailApi.connectAccountToUser(account);
		
		if(connectAccountToUser) {
			Account connectedAccount = this.accountService.save(account);
			return new ResponseEntity<AccountDTO>(new AccountDTO(connectedAccount), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
				
	}
	
	/**
	 * izmjena postojeceg naloga iz baze 
	 * @param accountDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Integer id) {
		
		Account account = accountService.findOne(id); 
		
		account.setSmtpAddress(accountDTO.getSmtpAddress());
		account.setSmtpPort(accountDTO.getSmtpPort()); 
		account.setInServerType(accountDTO.getInServerType());
		account.setInServerAddress(accountDTO.getInServerAddress());
		account.setInServerPort(accountDTO.getInServerPort());
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		
		account = accountService.save(account); 
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	/**
	 * brisanje naloga 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id) {
		Account account = accountService.findOne(id); 
		
		if(account != null) {
			accountService.removeAccount(id); 
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}