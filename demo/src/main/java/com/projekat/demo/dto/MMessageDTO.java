package com.projekat.demo.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;

public class MMessageDTO {
	
	private Integer id; 
	private String from; 
	//private Set<String> to; 
	//private Set<String> cc; 
	//private Set<String> bcc; 
	private String to; 
	private String cc; 
	private String bcc; 
	private Date dateTime; 
	private String subject; 
	private String content; 
	private boolean unread; 
	private Set<Attachment> attachments = new HashSet<Attachment>(); 
	private Account account; 
	private Set<Tag> tags = new HashSet<Tag>(); 
	private Folder folder;
	

	public MMessageDTO(Integer id, String from, String to, String cc, String bcc, Date dateTime, String subject,
			String content, boolean unread, Set<Attachment> attachments, Account account, Set<Tag> tags,
			Folder folder) {
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
		this.attachments = attachments;
		this.account = account;
		this.tags = tags;
		this.folder = folder;
	}
	
	public MMessageDTO(MMessage message) {
		this(message.getId(), message.getFrom(), message.getTo(), message.getCc(), message.getBcc(), message.getDateTime(), message.getSubject(),
				message.getContent(), message.getUnread(), message.getAttachments(), message.getAccount(), message.getTags(), message.getFolder());
	}

	
	/*
	public MMessageDTO(Integer id, String from, Set<String> to, Set<String> cc, Set<String> bcc, Date dateTime,
			String subject, String content, boolean unread, Set<Attachment> attachments, Account account, Set<Tag> tags,
			Folder folder) {
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
		this.attachments = attachments;
		this.account = account;
		this.tags = tags;
		this.folder = folder;
	}
*/
	/*
	public MMessageDTO(MMessage message) {
		this(message.getId(), message.getFrom(), message.getTo(), message.getCc(),message.getBcc(), message.getDateTime(), message.getSubject(), message.getContent(), 
				message.getUnread(), message.getAttachments(), message.getAccount(), message.getTags(), message.getFolder());
	}
	*/
	
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

	/*
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

*/
	public Date getDateTime() {
		return dateTime;
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

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	} 

}
