package com.projekat.demo.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.AccountRepository;

@Service
public class AccountService implements AccountServiceInterface {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserService userService; 

	public List<Account> findByUser(User user) {
		return accountRepository.findByUser(user);
	}
	
	public Account findByUsername(String username) {
		//Account account = accountRepository.findByUsername(username);
		return accountRepository.findByUsername(username);
	}

	@Override
	public List<Account> findAll() {
	return accountRepository.findAll();
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public List<Account> findAllByUserId(Integer id) {
		List<Account> accounts = accountRepository.findByUserId(id);
		return accounts;
	}

	public Account findByAccountIdAndUsername(Integer id, String username) {
		return accountRepository.findByUserIdAndUsername(id,username);
	}

	@Override
	public Account findOne(Integer accountId) {
		return accountRepository.findById(accountId);
	}

	@Override
	public void removeAccount(Account account) {
		accountRepository.delete(account);
	}
	
	public Account findByAccountId(int accountId, String username) {
		User user = userService.findByUsername(username);
		System.out.println("User je " + user); 
		
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

	public Account findAccount(Principal principal, int accountId) {
		User user = userService.findByUsername(principal.getName()); 
		
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



}
