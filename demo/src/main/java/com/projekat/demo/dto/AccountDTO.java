package com.projekat.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.User;
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
	private UserDTO user; 
	private List<FolderDTO> folders = new ArrayList<FolderDTO>();
	private List<MMessageDTO> messages = new ArrayList<MMessageDTO>();
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.smtpAddress = account.getSmtpAddress(); 
		this.smtpPort=account.getSmtpPort(); 
		this.inServerType=account.getInServerType(); 
		this.inServerAddress = account.getInServerAddress();
		this.inServerPort=account.getInServerPort();
		this.username=account.getUsername();
		this.password=account.getPassword();
		this.displayName=account.getDisplayName();
		this.user = new UserDTO(); 
		this.user.setId(account.getUser().getId());
		this.user.setUsername(account.getUser().getUsername());
		this.user.setPassword(account.getUser().getPassword());
		this.user.setFirstName(account.getUser().getFirstName());
		this.user.setLastName(account.getUser().getLastName());
		
		for(Folder folder : account.getFolders()) {
			folders.add(new FolderDTO(folder));
		}
		
		for(MMessage message : account.getMessages()) {
			messages.add(new MMessageDTO(message));
		}
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
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
