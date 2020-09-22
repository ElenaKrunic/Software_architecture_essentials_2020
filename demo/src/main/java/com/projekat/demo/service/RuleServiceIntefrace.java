package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;

public interface RuleServiceIntefrace {

	public Rule findOne(Integer ruleId);

	public Rule findByIdAndFolder(Integer id, Folder folder);
	
	public List<Rule> findByFolder(Folder folder);

	public List<Rule> findAll();

	public Rule save(Rule rule);

	public void remove(Integer id);
	
}
