package com.projekat.demo.service;

import java.util.List;
import java.util.Set;

import com.projekat.demo.entity.Account;

public interface AccountServiceInterface {
	
	List<Account> findAll();
	
	Account findOne(Integer accountId); 
	
	Account save(Account account);
	
	void removeAccount(Integer id); 
	
	List<Account> findAllByUserId(Integer id);
}
