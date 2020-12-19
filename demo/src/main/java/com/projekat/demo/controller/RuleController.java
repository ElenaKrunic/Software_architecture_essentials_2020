package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.RuleDTO;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.service.RuleServiceIntefrace;

@RestController
@RequestMapping("api/rules")
public class RuleController {

	@Autowired
	private RuleServiceIntefrace ruleService; 
	
	@GetMapping
	public ResponseEntity<List<RuleDTO>> getRules() {
		List<Rule> rules = ruleService.findAll(); 
		
		List<RuleDTO> rulesDTO = new ArrayList<RuleDTO>(); 
		
		for(Rule rule : rules) {
			rulesDTO.add(new RuleDTO(rule));
		}
		
		return new ResponseEntity<List<RuleDTO>>(rulesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<RuleDTO> getRule(@PathVariable("id") Integer id) {
		Rule rule = ruleService.findOne(id); 
		
		if(rule == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK); 
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<RuleDTO> saveRule(@RequestBody RuleDTO ruleDTO) {
		Rule rule = new Rule(); 
		
		rule.setCondition(ruleDTO.getCondition());
		//getuj folder --- opet ona prica sa objektom 
		rule.setOperation(ruleDTO.getOperation());
		rule.setValue(ruleDTO.getValue());
		
		ruleService.save(rule); 
		
		return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.CREATED); 
	}
	
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<RuleDTO> updateRule(@RequestBody RuleDTO ruleDTO, @PathVariable("id") Integer id) {
		Rule rule = ruleService.findOne(id);
		
		if(rule == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND);
		}
		
		rule.setCondition(ruleDTO.getCondition());
		//getuj folder --- opet ona prica sa objektom 
		rule.setOperation(ruleDTO.getOperation());
		rule.setValue(ruleDTO.getValue());
		
		ruleService.save(rule); 
		
		return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK); 
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteRule(@PathVariable("id") Integer id) {
		Rule rule = ruleService.findOne(id); 
		
		if(rule !=null) {
			ruleService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}
