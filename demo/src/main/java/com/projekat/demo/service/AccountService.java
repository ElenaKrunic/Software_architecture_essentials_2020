package com.projekat.demo.service;

import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.Aplikacija;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.AccountRepository;

@Service
public class AccountService implements AccountServiceInterface {

    private static final Logger LOGGER = LogManager.getLogger(Aplikacija.class);

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserService userService; 

	public List<Account> findByUser(User user) {
		LOGGER.info("Metoda u servisu pronalazi nalog za korisnika");
		return accountRepository.findByUser(user);
	}
	
	public Account findByUsername(String username) {
		//Account account = accountRepository.findByUsername(username);
		LOGGER.info("Metoda u servisu pronalazi nalog preko korisnickog imena");
		return accountRepository.findByUsername(username);
	}

	@Override
	public List<Account> findAll() {
		LOGGER.info("Metoda u servisu za pronalazenje svih naloga");
		return accountRepository.findAll();
	}

	@Override
	public Account save(Account account) {
		LOGGER.info("Metoda u servisu za cuvanje naloga! ");
		return accountRepository.save(account);
	}

	@Override
	public List<Account> findAllByUserId(Integer id) {
		List<Account> accounts = accountRepository.findByUserId(id);
		LOGGER.info("Metoda u trazenje naloga preko ID korisnika! ");
		return accounts;
	}

	public Account findByAccountIdAndUsername(Integer id, String username) {
		LOGGER.info("Metoda u servisu za trazenje naloga preko ID i korisnickog imena korisnika! ");
		return accountRepository.findByUserIdAndUsername(id,username);
	}

	@Override
	public Account findOne(Integer accountId) {
		LOGGER.info("Metoda u servisu za pronalazak naloga! ");
		return accountRepository.findById(accountId);
	}

	@Override
	public void removeAccount(Account account) {
		LOGGER.info("Metoda u servisu za brisanje naloga! ");
		accountRepository.delete(account);
	}
	
	public Account findByAccountId(int accountId, String username) {
		User user = userService.findByUsername(username);
		
		List<Account> userAccounts = user.getAccounts();
		
		Account account; 
		
		try {
			account = userAccounts.get(accountId);
			return account; 
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
	}

	public Account findAccount(Integer id, int accountId) {
		User user = userService.findOne(id);
		
		List<Account> userAccounts = user.getAccounts(); 
		
		
		try {
			for(Account account : userAccounts) {
				if(account.getId() == accountId) {
					LOGGER.info("Metoda u servisu za trazenje naloga preko ID-a! ");
					return account;
				}
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
