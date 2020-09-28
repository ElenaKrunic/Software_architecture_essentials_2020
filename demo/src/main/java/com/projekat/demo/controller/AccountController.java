package com.projekat.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AccountDTO;
import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.dto.UserDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AccountServiceInterface;
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping(value="api/accounts")
public class AccountController {
	
	@Autowired
	private AccountServiceInterface accountServiceInterface; 

	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private UserService userService; 
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = accountServiceInterface.findAll();
		
		List<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>(); 
		
		for(Account account : accounts) {
			dtoAccounts.add(new AccountDTO(account));
		}
		return new ResponseEntity<List<AccountDTO>>(dtoAccounts, HttpStatus.OK);  
	}
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Integer id) {
		
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK); 
	}
	
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<?> saveAccount(@RequestBody AccountDTO accountDTO) {
		
		if (accountDTO.getSmtpAddress().isEmpty())
			return new ResponseEntity<String>("Smtp Address can't be empty!", HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<String>("Display Name can't be empty!", HttpStatus.BAD_REQUEST);
		
		Account account = new Account(); 
		//account.setSmtpAddress(accountDTO.getSmtpAddress());
		//account.setSmtpPort(accountDTO.getSmtpPort()); 
		//account.setInServerType(accountDTO.getInServerType());
	//	account.setInServerAddress(accountDTO.getInServerAddress());
		//account.setInServerPort(accountDTO.getInServerPort());
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		
		account = accountService.save(account);
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes="application/json")
	//@RequestMapping(value="/{id}", consumes="application/json", method = RequestMethod.PUT)
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Integer id) {
		
		Account account = accountService.findOne(id); 
		
		//account.setSmtpAddress(accountDTO.getSmtpAddress());
		//account.setSmtpPort(accountDTO.getSmtpPort()); 
		//account.setInServerType(accountDTO.getInServerType());
		//account.setInServerAddress(accountDTO.getInServerAddress());
		//account.setInServerPort(accountDTO.getInServerPort());
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		
		account = accountService.save(account); 
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
		
	}

	
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
	
	/*
	@CrossOrigin
	@GetMapping("/{accountIndex}")
	public ResponseEntity<?> getAccountJedan(@PathVariable("accountIndex") Integer accountId, String username) {
		
		//ne prosljedjujem mu userDto username nego samo username 
		//mogu da prosliejdim u zagradu i {username} pa cu onda morati u URL da unesem ime iz baze 
		User user = userService.findByUsername(username);
		if(user == null) {
			System.out.println("Ne postoji korisnik"); 
		}
		
		List<Account> listaNaloga = accountService.findByUser(user); 
		
		if(listaNaloga == null) {
			return new ResponseEntity<String>("Ne postoji lista naloga za korisnika", HttpStatus.NOT_FOUND); 
		}
		
		AccountDTO dtoNalog = new AccountDTO(listaNaloga.get(accountId));
		
		return new ResponseEntity<AccountDTO>(dtoNalog, HttpStatus.OK);
	} */
	
	/*
	//pronalazi ali po username Accounta 
	@CrossOrigin
	@GetMapping("/{username}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("username") String username) {
		Account account = accountService.findByUsername(username);
		if(account != null) {
			return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
		}
		
        return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
	} */
	
	//==============================================================================================================
	//kad u url-u proslijedim username User-a 
	
	/*
	@GetMapping("/{username}")
	public ResponseEntity<?> getAllAccounts(@RequestParam("username") String username) {
		
		User user = userService.findByUsername(username);
		System.out.println("=============================== Ovo je user pronadjem po username ========================: " + user.getUsername()); 
		
		List<Account> accounts = accountService.findByUser(user); 
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>(); 
		for(Account account : accounts) {
			accountsDTO.add(new AccountDTO(account));
		}
		return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
	} 
	
	//==============================================================================================================
	 * */
	
}