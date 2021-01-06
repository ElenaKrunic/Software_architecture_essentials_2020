package com.projekat.demo.dto;

import java.io.Serializable;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.Rule;

public class RuleDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7652189145885234720L;
	private Integer id; 
	private Rule.RuleCondition condition; 
	private String value; 
	private Rule.RuleOperation operation;
	//private FolderDTO folder; 
	private Folder sourceFolder; 
	private Folder destinationFolder; 
	
	public RuleDTO() {}
	
	public RuleDTO(Integer id, Rule.RuleCondition condition, String value, Rule.RuleOperation operation, Folder sourceFolder, Folder destinationFolder) {
		this.id = id; 
		this.condition = condition;  
		this.value = value;  
		this.operation = operation;
		//this.folder = folder; 
		this.sourceFolder = sourceFolder; 
		this.destinationFolder = destinationFolder; 
	}
	
	// ne moze folderDTO, zato ide new FolderDTO(rule.getFolder) da mogu konvertovati u dto bean 
	public RuleDTO(Rule rule) {
		this(rule.getId(), rule.getCondition(), rule.getValue(), rule.getOperation(), rule.getSourceFolder(), rule.getDestinationFolder());
	}
	
	/*
	public FolderDTO getFolder() {
		return folder;
	}

	public void setFolder(FolderDTO folder) {
		this.folder = folder;
	}
	*/

	public Integer getId() {
		return id;
	}
	
	public Folder getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(Folder sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public Folder getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(Folder destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Rule.RuleCondition getCondition() {
		return condition;
	}
	
	public void setCondition(Rule.RuleCondition condition) {
		this.condition = condition;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Rule.RuleOperation getOperation() {
		return operation;
	}
	
	public void setOperation(Rule.RuleOperation operation) {
		this.operation = operation;
	} 
}
