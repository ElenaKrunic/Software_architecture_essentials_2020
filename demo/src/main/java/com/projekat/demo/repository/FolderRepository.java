package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

	Folder findInboxByAccount(String name, Account account);

}
