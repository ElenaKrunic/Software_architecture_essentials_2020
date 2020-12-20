package com.projekat.demo.repository;

import java.util.Date;
import java.util.List;

//import javax.mail.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;


public interface MMessageRepository extends JpaRepository<MMessage, Integer> {
	
	public List<MMessage> findAll();
	
	public MMessage findByIdAndAccount(Integer id, Account account);
	
	public List<MMessage> findAllByUnreadIsTrue();
	
	public List<MMessage> findAllByAccount(Account account);
	
	public List<MMessage> findAllByAccountAndFolder(Account account, Folder folder);
	
	@Query(value = "SELECT MAX(date_time) FROM messages WHERE account = :accountId", nativeQuery = true)
	public Date findLastDate(@Param("accountId") int accountId);
	
	public List<MMessage> findByFromContaining(String userEmail);
	
	//radjeno 18.12 
	List<MMessage> findByAccount(Account account);
	
}
