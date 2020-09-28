package com.projekat.demo.service;

import java.util.Date;
import java.util.List;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.MMessage;


public interface MessageServiceInterface {
	
	public MMessage findOne(Integer id, Account acconut);

	public List<MMessage> findAll(Account account);
	
	public List<MMessage> getMessages(Account account);
	
	public Date findLastDate(Account account);

	public MMessage save(MMessage message);

	public void remove(Integer id);
	
	public List<MMessage> findByFrom(String userEmail);

	public List<MMessage> findAllUnread(Account account);
	
}
