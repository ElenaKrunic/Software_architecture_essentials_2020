package com.projekat.demo.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;

public class MMessageDTO {
	
	StringTokenizer separator; 
	
	private Integer id; 
	private EmailDTO from; 
	private List<EmailDTO> to = new ArrayList<EmailDTO>();
	private List<EmailDTO> cc = new ArrayList<EmailDTO>();
	private List<EmailDTO> bcc = new ArrayList<EmailDTO>();
	private Timestamp dateTime; 
	private String subject; 
	private String content; 
	private Boolean unread; 
	
	private FolderDTO folder; 
	private AccountDTO account; 
	private UserDTO user;
	private List<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();
	private List<TagDTO> tags = new ArrayList<TagDTO>();
	
	//za pravljenje dto 
	public MMessageDTO(MMessage message) {
		this.id = message.getId(); 
		this.from = new EmailDTO(message.getFrom());
		
		StringTokenizer separator;
		
		if(message.getTo() != null) {
			separator = new StringTokenizer(message.getTo(), ",");
			while(separator.hasMoreElements()) {
				to.add(new EmailDTO(separator.nextToken()));
			}
		}
		
		if(message.getCc() !=null) {
			separator = new StringTokenizer(message.getCc(), ","); 
			while(separator.hasMoreElements()) {
				cc.add(new EmailDTO(separator.nextToken())); 
			}
		}
		
		if(message.getBcc() != null) {
			separator = new StringTokenizer(message.getBcc(), ","); 
			while(separator.hasMoreElements()) {
				bcc.add(new EmailDTO(separator.nextToken()));
			}
		}
		
		this.dateTime = message.getDateTime(); 
		this.subject = message.getSubject(); 
		this.content = message.getContent();
		this.unread = message.getUnread(); 
		
		this.folder = new FolderDTO();
		this.folder.setId(message.getFolder().getId()); 
		this.folder.setName(message.getFolder().getName());
		
		this.account = new AccountDTO(); 
		this.account.setId(message.getAccount().getId());
		this.account.setDisplayName(message.getAccount().getDisplayName());
		this.account.setSmtpAddress(message.getAccount().getSmtpAddress());
		this.account.setSmtpPort(message.getAccount().getSmtpPort());
		this.account.setInServerType(message.getAccount().getInServerType());
		this.account.setInServerAddress(message.getAccount().getInServerAddress()); 
		this.account.setInServerPort(message.getAccount().getInServerPort());
		this.account.setUsername(message.getAccount().getUsername());
		this.account.setPassword(message.getAccount().getPassword());
				
		for(Attachment attachment : message.getAttachments()) {
			attachments.add(new AttachmentDTO(attachment));
		}
		
		for(Tag tag: message.getTags()) {
			tags.add(new TagDTO(tag));
		}
	}
	
	
	public AccountDTO getAccount() {
		return account;
	}


	public void setAccount(AccountDTO account) {
		this.account = account;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EmailDTO getFrom() {
		return from;
	}
	public void setFrom(EmailDTO from) {
		this.from = from;
	}
	public List<EmailDTO> getTo() {
		return to;
	}
	public void setTo(List<EmailDTO> to) {
		this.to = to;
	}
	public List<EmailDTO> getCc() {
		return cc;
	}
	public void setCc(List<EmailDTO> cc) {
		this.cc = cc;
	}
	public List<EmailDTO> getBcc() {
		return bcc;
	}
	public void setBcc(List<EmailDTO> bcc) {
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
	public FolderDTO getFolder() {
		return folder;
	}
	public void setFolder(FolderDTO folder) {
		this.folder = folder;
	}
	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}
	public List<TagDTO> getTags() {
		return tags;
	}
	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	
	
	public static String recipientsToString(List<EmailDTO> recipients) {
		//kontra separatoru 
		StringBuilder builder = new StringBuilder();
		for(EmailDTO recipient : recipients) {
			builder.append(recipient.getEmail() + ", "); 
		}
		
		return (builder.length() > 0) ? builder.toString().substring(0, builder.length()-2) : builder.toString();
	}

}
