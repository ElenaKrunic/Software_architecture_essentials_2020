package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;

public class FolderDTO {
	
	private Integer id; 
	private String name;
	private FolderDTO parentFolder;
	private List<FolderDTO> subFolders = new ArrayList<FolderDTO>();
    private List<RuleDTO> rules = new ArrayList<RuleDTO>();
    private List<MMessageDTO> messages = new ArrayList<MMessageDTO>();
	private AccountDTO account; 

	public FolderDTO(Folder folder) {
		super();
		this.id = folder.getId();
		this.name = folder.getName();
		
		Folder parentFolder = folder.getParentFolder();
    	if (parentFolder != null) {
    		this.parentFolder = new FolderDTO();
        	this.parentFolder.setId(parentFolder.getId());
        	this.parentFolder.setName(parentFolder.getName());
        	this.parentFolder.setRules(new ArrayList<RuleDTO>());
        	this.parentFolder.setSubFolders(new ArrayList<FolderDTO>());
    	}
    	
    	for (Folder subFolder : folder.getSubFolders()) {
    		FolderDTO subFolderDTO = new FolderDTO();
    		subFolderDTO.setId(subFolder.getId());
    		subFolderDTO.setName(subFolder.getName());
    		subFolderDTO.setRules(new ArrayList<RuleDTO>());
    		subFolderDTO.setSubFolders(new ArrayList<FolderDTO>());
    		subFolders.add(subFolderDTO);
    	}
    	
    	this.account = new AccountDTO(); 
    	this.account.setId(folder.getAccount().getId());
    	this.account.setDisplayName(folder.getAccount().getDisplayName());
		this.account.setSmtpAddress(folder.getAccount().getSmtpAddress());
		this.account.setSmtpPort(folder.getAccount().getSmtpPort());
		this.account.setInServerType(folder.getAccount().getInServerType());
		this.account.setInServerAddress(folder.getAccount().getInServerAddress()); 
		this.account.setInServerPort(folder.getAccount().getInServerPort());
		this.account.setUsername(folder.getAccount().getUsername());
		this.account.setPassword(folder.getAccount().getPassword());
    	
    	for (Rule rule : folder.getRules()) {
    		rules.add(new RuleDTO(rule));
    	}
    	
    	for (MMessage message : folder.getMessages()) {
    		messages.add(new MMessageDTO(message));
    	}
	}
	
	public FolderDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FolderDTO getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(FolderDTO parentFolder) {
		this.parentFolder = parentFolder;
	}

	public List<FolderDTO> getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(List<FolderDTO> subFolders) {
		this.subFolders = subFolders;
	}

	public List<RuleDTO> getRules() {
		return rules;
	}

	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}

	public List<MMessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MMessageDTO> messages) {
		this.messages = messages;
	} 
	
}
