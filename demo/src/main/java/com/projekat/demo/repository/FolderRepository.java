package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
	

	//public Folder findInboxByAccount(String name, Account account);
	
	public List<Folder> findAllByAccountAndParentFolderIsNull(Account account);
	
	public Folder findByNameAndAccount(String name, Account account);
	
	public List<Folder> findAllByAccount(Account account);
	
	public Folder findByIdAndAccount(Integer id, Account account);
	

}
