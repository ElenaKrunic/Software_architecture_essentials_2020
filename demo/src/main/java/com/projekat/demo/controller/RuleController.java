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
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.RuleServiceIntefrace;

@RestController
@RequestMapping("api/rules")
public class RuleController {

	@Autowired
	private RuleServiceIntefrace ruleService; 
	
	@Autowired
	private FolderService folderService; 
	
	@GetMapping(value="/getRules")
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
	
	@PostMapping(value="/saveRule/{sourceFolderId}/{destinationFolderId}")
	public ResponseEntity<RuleDTO> saveRule(@RequestBody RuleDTO ruleDTO, @PathVariable("sourceFolderId") Integer sourceFolderId, @PathVariable("destinationFolderId") Integer destinationFolderId){
		
		Folder sourceFolder = folderService.findById(sourceFolderId);
		
		if(sourceFolder == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
				
		Folder destinationFolder = folderService.findById(destinationFolderId); 
		
		if(destinationFolder == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
				
		Rule rule = new Rule();
		rule.setCondition(ruleDTO.getCondition());
		rule.setOperation(ruleDTO.getOperation());
		rule.setValue(ruleDTO.getValue());
		sourceFolder.addRuleToSourceFolder(rule);
		destinationFolder.addRuleToDestinationFolder(rule);
		
		rule = ruleService.save(rule);
		
		return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.CREATED);
	}
	
	
	@PutMapping(value="/updateRule/{ruleId}/{sourceFolderId}/{destinationFolderId}")
	public ResponseEntity<RuleDTO> updateRule(@RequestBody RuleDTO ruleDTO, @PathVariable("ruleId") Integer ruleId, @PathVariable("sourceFolderId") Integer sourceFolderId, @PathVariable("destinationFolderId")Integer destinationFolderId){
		
		Rule rule = ruleService.findOne(ruleId);
		
		if(rule == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
		
	Folder sourceFolder = folderService.findById(sourceFolderId);
		
		if(sourceFolder == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
				
		Folder destinationFolder = folderService.findById(destinationFolderId); 
		
		if(destinationFolder == null) {
			return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND); 
		}
		
		rule.setCondition(ruleDTO.getCondition());
		rule.setOperation(ruleDTO.getOperation());
		rule.setValue(ruleDTO.getValue());
		sourceFolder.addRuleToSourceFolder(rule);
		destinationFolder.addRuleToDestinationFolder(rule);
		
		rule = ruleService.save(rule);
		
		return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);
	}
		
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteRule(@PathVariable("id") Integer id) {
		Rule rule = ruleService.findOne(id); 
		
		if(rule !=null) {
			ruleService.remove(rule);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	
	
}
