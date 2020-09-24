package com.projekat.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Account;
import com.projekat.demo.repository.AccountRepository;

@Service
public class AccountService implements AccountServiceInterface {

	@Autowired
	AccountRepository accountRepository;
	
	//@Override
	//public Account findOne(Integer accountId) {
		//return accountRepository.findOne(accountId);
	//}

}
