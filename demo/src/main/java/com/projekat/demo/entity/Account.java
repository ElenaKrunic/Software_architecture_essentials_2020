package com.projekat.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="accounts")
public class Account implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "smtp_address", columnDefinition = "VARCHAR(250)", nullable = false)
	private String smtpAddress;

	@Column(name = "smtp_port", columnDefinition = "INT", nullable = false)
	private int smtpPort;

	@Column(name = "in_server_type", columnDefinition = "TINYINT", nullable = false)
	private int inServerType;

	@Column(name = "in_server_address", columnDefinition = "VARCHAR(250)", nullable = false)
	private String inServerAddress;

	@Column(name = "in_server_port", columnDefinition = "INT", nullable = false)
	private int inServerPort;

	@Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
	private String username;

	@Column(name = "password", columnDefinition = "VARCHAR(50)", nullable = false)
	private String password;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String displayName;
	
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "account")
	private Set<Folder> folders = new HashSet<Folder>();
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "account")
	private Set<MMessage> messages = new HashSet<MMessage>(); 

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;
	
	@Column(name = "last_sync_time", columnDefinition = "TIMESTAMP", nullable = true)
	private Timestamp lastSyncTime;
	
	public Account() {
		
	}
	
	public void addMessage(MMessage message) {
		if(message.getAccount() != null) 
			message.getAccount().removeMessage(message);
		message.setAccount(this);
		getMessages().add(message);
	}

	public void removeMessage(MMessage message) {
		message.setAccount(null);
		getMessages().remove(message);
	}
	
	public void addFolder(Folder folder) {
		if(folder.getAccount()!= null)
			folder.getAccount().removeFolder(folder);
		folder.setAccount(this);
		getFolders().add(folder);
	}
	
	private void removeFolder(Folder folder) {
		folder.setAccount(null);
		getFolders().remove(folder);
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

	public Set<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	public Set<MMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<MMessage> messages) {
		this.messages = messages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Timestamp lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}

