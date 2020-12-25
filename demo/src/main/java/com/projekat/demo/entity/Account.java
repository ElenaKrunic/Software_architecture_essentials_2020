package com.projekat.demo.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "smtp_address", columnDefinition = "VARCHAR(250)", nullable = true)
	private String smtpAddress;

	@Column(name = "smtp_port", columnDefinition = "INT", nullable = true)
	private int smtpPort;

	@Column(name = "in_server_type", columnDefinition = "TINYINT", nullable = true)
	private int inServerType;

	@Column(name = "in_server_address", columnDefinition = "VARCHAR(250)", nullable = true)
	private String inServerAddress;

	@Column(name = "in_server_port", columnDefinition = "INT", nullable = true)
	private int inServerPort;

	@Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = true)
	private String username;

	@Column(name = "password", columnDefinition = "VARCHAR(50)", nullable = true)
	private String password;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = true)
	private String displayName;
	
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "account")
	private Set<Folder> folders = new HashSet<Folder>();
	
	@JsonIgnore
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "account")
	private Set<MMessage> messages = new HashSet<MMessage>(); 

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;
	
	@Column(name = "last_sync_time", columnDefinition = "TIMESTAMP", nullable = true)
	private Timestamp lastSyncTime;
	
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", displayName=" + displayName + "]";
	}

	public Timestamp getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Timestamp lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	/*
	public void addFolder(Folder folder) {
		if(folder.getAccount() != null) {
			folder.getAccount().removeFolder(folder); 
			folder.setAccount(this);
			getFolders().add(folder);
		}
	}
	*/
	
}

