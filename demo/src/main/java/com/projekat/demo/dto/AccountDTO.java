package com.projekat.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projekat.demo.entity.Account;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {

	private Integer id; 
	private String smtpAddress;
	private int smtpPort; 
	private int inServerType; 
	private String inServerAddress; 
	private int inServerPort; 
	private String username; 
	private String password; 
	private String displayName;
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.smtpAddress = account.getSmtpAddress(); 
		this.smtpPort=account.getSmtpPort(); 
		this.inServerType=account.getInServerType(); 
		this.inServerPort=account.getInServerPort();
		this.username=account.getUsername();
		this.password=account.getPassword();
		this.displayName=account.getDisplayName();
	}
	
	public AccountDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public int getInServerType() {
		return inServerType;
	}

	public void setInServerType(int inServerType) {
		this.inServerType = inServerType;
	}

	public String getInServerAddress() {
		return inServerAddress;
	}

	public void setInServerAddress(String inServerAddress) {
		this.inServerAddress = inServerAddress;
	}

	public int getInServerPort() {
		return inServerPort;
	}

	public void setInServerPort(int inServerPort) {
		this.inServerPort = inServerPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public AccountDTO(Integer id, String smtpAddress, int smtpPort, int inServerType, String inServerAddress,
			int inServerPort, String username, String password, String displayName) {
		super();
		this.id = id;
		this.smtpAddress = smtpAddress;
		this.smtpPort = smtpPort;
		this.inServerType = inServerType;
		this.inServerAddress = inServerAddress;
		this.inServerPort = inServerPort;
		this.username = username;
		this.password = password;
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", smtpAddress=" + smtpAddress + ", smtpPort=" + smtpPort + ", inServerType="
				+ inServerType + ", inServerAddress=" + inServerAddress + ", inServerPort=" + inServerPort
				+ ", username=" + username + ", password=" + password + ", displayName=" + displayName + "]";
	}

	
	
	
	
}
