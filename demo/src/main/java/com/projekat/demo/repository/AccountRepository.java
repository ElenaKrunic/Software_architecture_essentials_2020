package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	//Account findOne(Integer accountId);

}
