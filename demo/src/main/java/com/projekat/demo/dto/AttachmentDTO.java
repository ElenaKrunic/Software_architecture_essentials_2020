package com.projekat.demo.dto;

import com.projekat.demo.entity.Attachment;

public class AttachmentDTO {
	
	private Integer id; 
	private String data; 
	private String mimeType; 
	private String name; 
	
	private MMessageDTO message;
	
	public AttachmentDTO(Attachment attachment) {
		this.id=attachment.getId();
		this.data=attachment.getData();
		this.mimeType=attachment.getMimeType();
		this.name=attachment.getName();
		
		this.message = new MMessageDTO();		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "AttachmentDTO [id=" + id + ", data=" + data + ", mimeType=" + mimeType + ", name=" + name + "]";
	}
	public AttachmentDTO(Integer id, String data, String mimeType, String name) {
		super();
		this.id = id;
		this.data = data;
		this.mimeType = mimeType;
		this.name = name;
	}
	public AttachmentDTO() {
		super();
	} 
	
	

}
