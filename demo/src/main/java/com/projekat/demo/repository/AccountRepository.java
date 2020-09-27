package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	List<Account> findByUser(User user);

	//Account findOne(Integer accountId);
	
	Account findByUsername(String username); 

}
