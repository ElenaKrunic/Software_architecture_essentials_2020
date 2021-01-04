package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Account;

public interface AccountServiceInterface {
	
	List<Account> findAll();
	
	Account findOne(Integer accountId); 
	
	Account save(Account account);
		
	List<Account> findAllByUserId(Integer id);

	void removeAccount(Account account);
}
