package com.projekat.demo.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Table(name="messages")
public class MMessage {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 

	@Column(name="from", unique=false, nullable=true)
	private String from;
	
	@Column(name="to", unique=false, nullable=true)
	private String to; 
	
	@Column(name="cc", unique=false, nullable=true)
	private String cc; 
	
	@Column(name="bcc",unique=false, nullable=true)
	private String bcc; 
	
	@Column(name="dateTime", unique=false, nullable=false)
	private Timestamp dateTime;
	
	@Column(name="subject", unique=false, nullable=true)
	private String subject; 
	
	@Column(name="content", unique=false, nullable=true)
	private String content; 
	
	@Column(name="unread", unique=false, nullable=true)
	private Boolean unread;
	
	//veza many to many - tagovi i poruke 
	//many to one - folder i poruke 
	//many to one - account i poruke 
	//onetomany attachment i poruke
	
	@ManyToOne
	@JoinColumn(name="folder", referencedColumnName="id", nullable=false)
	private Folder folder; 
	
	@ManyToOne
	@JoinColumn(name="account", referencedColumnName="id", nullable=false)
	private Account account; 
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(name="message_tags", joinColumns= @JoinColumn(name="message_id",referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "id"))
	private List<Tag> tags;
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="message")
	private List<Attachment> attachments;

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

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
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

	public MMessage(Integer id, String from, String to, String cc, String bcc, Timestamp dateTime, String subject,
			String content, Boolean unread) {
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
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", from=" + from + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", dateTime="
				+ dateTime + ", subject=" + subject + ", content=" + content + ", unread=" + unread + "]";
	}

	public MMessage() {
		super();
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	} 
	
	
}
