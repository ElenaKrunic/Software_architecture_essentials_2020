package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.repository.RuleRepository;

@Service
public class RuleService implements RuleServiceIntefrace {

	@Autowired
	RuleRepository ruleRepository;
	
	
	@Override
	public Rule findOne(Integer ruleId) {
		return ruleRepository.getOne(ruleId); 
	}

	@Override
	public List<Rule> findAll() {
		return ruleRepository.findAll();
	}

	@Override
	public Rule save(Rule rule) {
		return ruleRepository.save(rule);
	}

	@Override
	public void remove(Rule rule) {
		ruleRepository.delete(rule);
		
	}

	@Override
	public Rule findByIdAndSourceFolder(Integer id, Folder folder) {
		return ruleRepository.findByIdAndSourceFolder(id, folder);
	}

	@Override
	public List<Rule> findBySourceFolder(Folder folder) {
		return ruleRepository.findBySourceFolder(folder);
	}
	
}
