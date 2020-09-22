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
	public Rule findByIdAndFolder(Integer id, Folder folder) {
		return ruleRepository.findByIdAndFolder(id, folder); 
	}

	@Override
	public List<Rule> findByFolder(Folder folder) {
		return ruleRepository.findAllByFolder(folder);
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
	public void remove(Integer id) {
		ruleRepository.deleteById(id);
		
	}
}
