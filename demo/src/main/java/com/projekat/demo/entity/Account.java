package com.projekat.demo.entity;

import java.util.List;

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

@Entity
@Table(name="accounts")
public class Account {
	
	// ----- FIELDS

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

	@Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = true)
	private String username;

	@Column(name = "password", columnDefinition = "VARCHAR(50)", nullable = true)
	private String password;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String displayName;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "account")
	private List<Folder> folders;

	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;


	// ----- GETTERS AND SETTERS

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

	public List<Folder> getFolders() {
		return folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", displayName=" + displayName + "]";
	}

/*
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="smtpAddress", nullable=false)
	private String smtpAddress;
	
	@Column(name="smtpPort", nullable=false)
	private Integer smtpPort; 
	
	@Column(name="inServerType", nullable=false)
	private Integer inServerType;
	
	@Column(name="inServerAddress",nullable=false)
	private String inServerAddress;
	
	@Column(name="inServerPort",nullable=false)
	private Integer inServerPort;
	
	@Column(name="username", unique=true, nullable=false)
	private String username; 
	
	@Column(name="password", unique=true, nullable=false)
	private String password; 
	
	@Column(name="displayname", unique=false, nullable=true)
	private String displayname;
	
	//veza: many to one za user 
	//veza: one to many folderi 
 
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="account")
	private List<Folder> folders; 

	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;

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

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Integer getInServerType() {
		return inServerType;
	}

	public void setInServerType(Integer inServerType) {
		this.inServerType = inServerType;
	}

	public String getInServerAddress() {
		return inServerAddress;
	}

	public void setInServerAddress(String inServerAddress) {
		this.inServerAddress = inServerAddress;
	}

	public Integer getInServerPort() {
		return inServerPort;
	}

	public void setInServerPort(Integer inServerPort) {
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

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public List<Folder> getFolders() {
		return folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account(Integer id, String smtpAddress, Integer smtpPort, Integer inServerType, String inServerAddress,
			Integer inServerPort, String username, String password, String displayname, List<Folder> folders,
			User user) {
		super();
		this.id = id;
		this.smtpAddress = smtpAddress;
		this.smtpPort = smtpPort;
		this.inServerType = inServerType;
		this.inServerAddress = inServerAddress;
		this.inServerPort = inServerPort;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
		this.folders = folders;
		this.user = user;
	}

	public Account() {
		super();
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", smtpAddress=" + smtpAddress + ", smtpPort=" + smtpPort + ", inServerType="
				+ inServerType + ", inServerAddress=" + inServerAddress + ", inServerPort=" + inServerPort
				+ ", username=" + username + ", password=" + password + ", displayname=" + displayname + ", folders="
				+ folders + ", user=" + user + "]";
	}
	*/
}

