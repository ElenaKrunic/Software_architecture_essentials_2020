package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.repository.RuleRepository;

@Service
public class RuleService implements RuleServiceIntefrace {
	
    private static final Logger LOGGER = LogManager.getLogger(RuleService.class);

	@Autowired
	RuleRepository ruleRepository;
	
	
	@Override
	public Rule findOne(Integer ruleId) {
		LOGGER.info("Metoda iz servisa za pronalazenje jednog pravila"); 
		return ruleRepository.getOne(ruleId); 
	}

	@Override
	public List<Rule> findAll() {
		LOGGER.info("Metoda iz servisa za pronalazenje svih pravila"); 
		return ruleRepository.findAll();
	}

	@Override
	public Rule save(Rule rule) {
		LOGGER.info("Metoda iz servisa za cuvanje pravila"); 
		return ruleRepository.save(rule);
	}

	@Override
	public void remove(Rule rule) {
		LOGGER.info("Metoda iz servisa za brisanje jednog pravila"); 
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
