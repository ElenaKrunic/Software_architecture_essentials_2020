package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AccountDTO;
import com.projekat.demo.dto.UserDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private UserService userService; 
	
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
	
	
	//pronalazi ali po username naloga 
	@CrossOrigin
	@GetMapping("/{username}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("username") String username) {
		Account account = accountService.findByUsername(username);
		if(account != null) {
			return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
		}
		
        return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
	}
	
	//kako da pronadje po korisniku
	@CrossOrigin
	@GetMapping
	public ResponseEntity<?> getAllAccounts(User user) {
		
		user = userService.findByUsername(user.getUsername());
		
		List<Account> accounts = accountService.findByUser(user); 
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>(); 
		for(Account account : accounts) {
			accountsDTO.add(new AccountDTO(account));
		}
		return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
	}
	
	
}
