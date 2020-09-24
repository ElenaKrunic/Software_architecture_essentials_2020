package com.projekat.demo.repository;

import java.util.List;

//import javax.mail.Message;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;


public interface MMessageRepository extends JpaRepository<MMessage, Integer> {

	//List<MMessage> findAllMessagesByAccount(Account account);

	//List<MMessage> findAllMessagesByAccount(Account account, Folder folder);

}
