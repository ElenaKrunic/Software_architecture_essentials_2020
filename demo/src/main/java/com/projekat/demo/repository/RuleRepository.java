package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
	
	
	public Rule findByIdAndSourceFolder(Integer id, Folder folder);
	
	public List<Rule> findBySourceFolder(Folder folder);
	
}
