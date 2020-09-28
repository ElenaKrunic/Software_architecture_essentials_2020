package com.projekat.demo.service;

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
	public Account findOne(Integer accountId) {
		return accountRepository.getOne(accountId);
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public void removeAccount(Integer id) {
		accountRepository.deleteById(id);
		
	}

	
	

}
