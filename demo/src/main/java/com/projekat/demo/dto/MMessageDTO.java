package com.projekat.demo.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;

public class MMessageDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1364408015539579586L;
	
	private Integer id; 
	private String from; 
	private String to; 
	private String cc; 
	private String bcc; 
	//private LocalDateTime dateTime; 
	private Timestamp dateTime; 
	private String subject; 
	private String content; 
	private boolean unread; 
	private Set<AttachmentDTO> attachments = new HashSet<AttachmentDTO>();	
	private Set<TagDTO> tags = new HashSet<TagDTO>(); 

	
	public MMessageDTO(Integer id, String from, String to, String cc, String bcc, Timestamp dateTime, String subject,
			String content, boolean unread) {
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
	
		
	public MMessageDTO(MMessage message) {
		this(message.getId(), message.getFrom(), message.getTo(), message.getCc(), message.getBcc(), message.getDateTime(), message.getSubject(),
				message.getContent(), message.getUnread());
		
		for(Tag tag : message.getTags()) {
			this.tags.add(new TagDTO(tag));
		}
		
		for(Attachment attachment : message.getAttachments()) {
			this.attachments.add(new AttachmentDTO(attachment));
		}
	}

	
	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public Set<AttachmentDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}

	
	
	public MMessageDTO() {
		
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

//	public LocalDateTime getDateTime() {
	//	return dateTime;
//	}

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

	//public void setDateTime(LocalDateTime dateTime) {
	//	this.dateTime = dateTime;
	//}

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

	public Set<TagDTO> getTags() {
		return tags;
	}

	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}
	

	public Set<AttachmentDTO> getAttachmentsDTO() {
		return attachments;
	}

	public void setAttachmentsDTO(Set<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}

	public static String recipientsToString(List<EmailDTO> emails) {
		StringBuilder builder = new StringBuilder(); 
		
		for(EmailDTO email : emails ) {
			builder.append(email.getEmail() + ", ");
		}
		
		int length = builder.length();
		
		return (length > 0) ? builder.toString().substring(0, length -2) : builder.toString();
	}

}
