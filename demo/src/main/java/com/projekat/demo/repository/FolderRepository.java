package com.projekat.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {
			
	public Set<Folder> findByAccountIdAndParentFolder(Integer accountId, Integer parentFolderId);
	
	public Folder findByNameAndAccount(String name, Account account);
	
	//ne diraj 
	public List<Folder> findAllByAccount(Account account);
		
	public Folder findById(Integer id);

}
