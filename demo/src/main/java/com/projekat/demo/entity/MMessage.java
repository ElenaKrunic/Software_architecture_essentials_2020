package com.projekat.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class MMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "message_from", columnDefinition = "VARCHAR(100)", unique = false, nullable = false)
	private String from;
	
	@Column(name = "message_to", unique = false, nullable = false)
	private String to;
	
	@Column(name = "cc", unique = false, nullable = true)
	private String cc;
	
	@Column(name = "bcc", unique = false, nullable = true)
	private String bcc;

	//RADI TESTIRANJA SAM STAVILA DA MI JE NULLABLE TRUE
	//@Column(name = "date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	//private LocalDateTime dateTime;
	
	@Column(name="date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private Timestamp dateTime;

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	@Column(name = "subject", columnDefinition = "VARCHAR(250)", unique = false, nullable = false)
	private String subject;

	@Column(name = "content", columnDefinition = "TEXT", unique = false, nullable = false)
	private String content;

	@Column(name = "unread", columnDefinition = "BIT default 1", unique = false, nullable = false)
	private Boolean unread;
	
	@ManyToOne
	@JoinColumn(name = "folder", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Folder folder; 
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
	private Account account;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "message")
	@JsonIgnore
	private List<Attachment> attachments = new ArrayList<Attachment>();

	public Timestamp getDateTime() {
		return dateTime;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "message_tags", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
	private List<Tag> tags = new ArrayList<Tag>();
	
	public MMessage() {
		
	}
	
	public void addAttachment(Attachment attachment) {
		if(attachment.getMessage()!=null) 
			attachment.getMessage().removeAttachment(attachment);
		attachment.setMessage(this);
		getAttachments().add(attachment);
	}
	private void removeAttachment(Attachment attachment) {
		attachment.setMessage(null);
		getAttachments().remove(attachment);
	}
	
	public void addTag(Tag tag) {
		tag.getMessages().add(this); 
		getTags().add(tag);
	}

	public void removeTag(Tag tag) {
		tag.getMessages().remove(this);
		getTags().remove(tag);
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
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/*
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	*/

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

	public MMessage(Integer id, String from, String to, String cc, String bcc, Timestamp dateTime, String subject,
			String content, Boolean unread, Folder folder, Account account, List<Attachment> attachments,
			List<Tag> tags) {
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
		this.account = account;
		this.attachments = attachments;
		this.tags = tags;
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

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public static MMessage clone(MMessage message) {
		MMessage copy = new MMessage();
		copy.setFrom(message.getFrom());
		copy.setTo(message.getTo());
		copy.setCc(message.getCc());
		copy.setBcc(message.getBcc());
		copy.setDateTime(message.getDateTime());
		copy.setSubject(message.getSubject());
		copy.setContent(message.getContent());
		copy.setUnread(message.unread);
		message.getAccount().addMessage(copy);
		for (Tag tag : message.getTags())
			copy.addTag(tag);
		for (Attachment attachment : message.getAttachments())
			copy.addAttachment(Attachment.clone(attachment));
		
		return copy;
	}

	public MMessage(MMessage message) {
		super();
		this.id = message.getId();
		this.from = message.getFrom();
		this.to = message.getTo();
		this.cc = message.getCc();
		this.bcc = message.getBcc();
		this.dateTime = message.getDateTime();
		this.subject = message.getSubject();
		this.content = message.getContent();
		this.unread = message.getUnread();
		this.folder = message.getFolder();
		this.attachments = new ArrayList<Attachment>();
		for (Attachment attachment : message.getAttachments())
			this.attachments.add(attachment);
		
		this.tags = new ArrayList<Tag>();
		for (Tag tag : message.getTags())
			this.tags.add(tag);
		
		this.account = message.getAccount();
	}

}
