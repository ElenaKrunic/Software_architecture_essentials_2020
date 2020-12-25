package com.projekat.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="messages")
public class MMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "message_from", columnDefinition = "VARCHAR(100)", unique = false, nullable = false)
	private String from;
	
	@ElementCollection
	@CollectionTable(name="message_to", joinColumns = @JoinColumn(name = "message_id"))
	@Column(name="message_to")
	private Set<String> to;
	
	@ElementCollection
	@CollectionTable(name="message_cc", joinColumns = @JoinColumn(name = "message_id"))
	@Column(name="message_cc")
	private Set<String> cc; 
	
	@ElementCollection
	@CollectionTable(name="message_bcc", joinColumns = @JoinColumn(name = "message_id"))
	@Column(name="message_bcc")
	private Set<String> bcc;

	@Column(name = "date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private Date dateTime;

	@Column(name = "subject", columnDefinition = "VARCHAR(250)", unique = false, nullable = true)
	private String subject;

	@Column(name = "content", columnDefinition = "TEXT", unique = false, nullable = true)
	private String content;

	@Column(name = "unread", columnDefinition = "BIT default 1", unique = false, nullable = false)
	private Boolean unread;
	
	@ManyToOne
	@JoinColumn(name = "folder", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Folder folder; 

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "message")
	private Set<Attachment> attachments = new HashSet<Attachment>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "message_tags", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
	private Set<Tag> tags = new HashSet<Tag>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
	private Account account;
	
	public MMessage() {
		
	}
	
	public MMessage(Integer id, String from, Set<String> to, Set<String> cc, Set<String> bcc, Date dateTime,
			String subject, String content, Boolean unread, Folder folder, Set<Attachment> attachments, Set<Tag> tags,
			Account account) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.dateTime = dateTime;
		this.subject = subject;
		this.content = content;
		this.unread = unread;
		this.folder = folder;
		this.attachments = attachments;
		this.tags = tags;
		this.account = account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Set<String> getTo() {
		return to;
	}

	public void setTo(Set<String> to) {
		this.to = to;
	}

	public Set<String> getCc() {
		return cc;
	}

	public void setCc(Set<String> cc) {
		this.cc = cc;
	}

	public Set<String> getBcc() {
		return bcc;
	}

	public void setBcc(Set<String> bcc) {
		this.bcc = bcc;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getUnread() {
		return unread;
	}

	public void setUnread(Boolean unread) {
		this.unread = unread;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
