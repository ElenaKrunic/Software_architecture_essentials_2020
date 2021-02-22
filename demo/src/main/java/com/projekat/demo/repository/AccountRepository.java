package com.projekat.demo.repository;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.Aplikacija;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	List<Account> findByUser(User user);

	Account findByUsername(String username); 
	
	List<Account> findByUserId(Integer id);

	Account findByUserIdAndUsername(Integer id, String username);
	
	Account findById(Integer id);
	
}
