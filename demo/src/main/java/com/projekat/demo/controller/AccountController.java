package com.projekat.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.projekat.demo.Aplikacija;
import com.projekat.demo.dto.AccountDTO;
import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AccountServiceInterface;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.UserService;

/**
 * Kontroler koji radi sa nalogom. 
 * Metode koje su implementirane: 
 *  1. Pronalazenje svih naloga za korisnika 
 *  2. Pronalazenje pojedinackog naloga za korisnika 
 *  3. Pronalazenje liste svih poruka za nalog 
 *  4. Pronalazenje liste svih foldera za nalog 
 *  5. Pronalazenje svih naloga iz aplikacije 
 *  6. Dodavanje novog naloga 
 *  7. Izmjena postojeceg naloga 
 *  8. Brisanje naloga 
 * @author Elena Krunic 
 *
 */
@RestController
@RequestMapping(value="api/accounts")
public class AccountController {
	
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
	
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
	
	@Autowired 
	private MailAPI mailApi;
		
	
	/**
	 * 
	 * @param id korisnika cije naloge zelimo ispisati  
	 * @return lista naloga za korisnika 
	 */
	@GetMapping("/getAccountsForUser")
	public ResponseEntity<List<AccountDTO>> getAllAccountsForUser() {
		List<Account> accounts = accountService.findAllByUserId(UserController.korisnikID);
		List<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>(); 
		if(accounts == null) {
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.BAD_REQUEST);
		}
		for(Account account : accounts) {
			AccountDTO dto = new AccountDTO(account); 
			dtoAccounts.add(dto);
		}
		
		LOGGER.info("Vracena lista naloga za jednog korisnika");
		
		return new ResponseEntity<List<AccountDTO>>(dtoAccounts, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id korisnika 
	 * @return pojedinacan nalog za korisnika 
	 */
	@GetMapping(value="/{accountIndex}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("accountIndex") Integer id){
		Account account = accountService.findOne(id);
		if(account == null){
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		
		LOGGER.info("Vracen nalog za jednog korisnika");
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param id naloga 
	 * @return lista poruka za nalog 
	 */
	@GetMapping(value="/{id}/messages")
	public ResponseEntity<List<MMessageDTO>> getMessageByAccount(@PathVariable("id") Integer id) {
		System.out.println(id.toString());
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
			
			LOGGER.info("Vracena lista poruka za nalog"); 
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
			
			LOGGER.info("Vracena lista poruka za foldera"); 
			return new ResponseEntity<List<FolderDTO>>(dtoFolders, HttpStatus.OK);
		}
	}
	
	/**
	 * Metoda za ispis svih naloga iz aplikacije 
	 * @return listu naloga 
	 */
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = accountServiceInterface.findAll();
		
		List<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>(); 
		
		for(Account account : accounts) {
			dtoAccounts.add(new AccountDTO(account));
		}
		
		LOGGER.info("Metoda za sve naloge iz aplikacije"); 

		return new ResponseEntity<List<AccountDTO>>(dtoAccounts, HttpStatus.OK);  
	}
		
	/**
	 * dodavanje novog naloga 
	 * @param accountDTO 
	 * @return kreiran nalog u bazi 
	 */
	@PostMapping("/saveAccount/{userUsername}")
	public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO, @PathVariable("userUsername") String username) {
			
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
			
			LOGGER.info("Uspjesno izvrseno dodavanje novog naloga"); 

			return new ResponseEntity<AccountDTO>(new AccountDTO(connectedAccount), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
				
	}
	
	/**
	 * izmjena postojeceg naloga iz baze 
	 * @param accountDTO
	 * @param id naloga koji mijenjamo 
	 * @return izmijenjen nalog u bazi 
	 */
	@PutMapping(value="updateAccount/{accountId}", consumes="application/json")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("accountId") Integer id) {
		
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		
		account.setUsername(accountDTO.getUsername());
		account.setDisplayName(accountDTO.getDisplayName());
		if(accountDTO.getPassword() != null) {
			account.setPassword(accountDTO.getPassword());
		}
		account.setSmtpAddress(accountDTO.getSmtpAddress());
		account.setSmtpPort(accountDTO.getSmtpPort()); 
		account.setInServerType(accountDTO.getInServerType());
		account.setInServerAddress(accountDTO.getInServerAddress());
		account.setInServerPort(accountDTO.getInServerPort());
		
		MailAPI mailApi = new MailAPI();
		boolean connectAccountToUser = mailApi.connectAccountToUser(account);
		
		if(connectAccountToUser) {
			Account connectedAccount = this.accountService.save(account);
			
			LOGGER.info("Uspjesno uradjena izmjena naloga"); 

			return new ResponseEntity<AccountDTO>(new AccountDTO(connectedAccount), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * brisanje naloga 
	 * @param id
	 * @return obrisan nalog 
	 */
	@DeleteMapping(value="deleteAccount/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id) {
		Account account = accountService.findOne(id); 
		
		if(account != null) {
			accountService.removeAccount(account); 
			
			LOGGER.info("Uspjesno odradjeno brisanje naloga"); 

			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
}