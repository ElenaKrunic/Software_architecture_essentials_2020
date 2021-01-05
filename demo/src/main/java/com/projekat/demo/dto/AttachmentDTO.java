package com.projekat.demo.dto;

import com.projekat.demo.entity.Attachment;
import com.projekat.demo.util.Base64;
import com.projekat.demo.util.FilesUtil;

public class AttachmentDTO {
	
	private Integer id; 
	private String data; 
	private String mimeType; 
	private String name; 

	public AttachmentDTO() {} 
	
	public AttachmentDTO(Integer id, String data, String mimeType, String name) {
		this.id=id;
		this.data=data;
		this.mimeType=mimeType;
		this.name=name;
	}
	
	public AttachmentDTO(Attachment attachment) {
		this(attachment.getId(), attachment.getData() ,attachment.getMimeType(), attachment.getName());
		
		if (attachment.getData() != null && !attachment.getData().isEmpty()) {
			byte[] attachmentData = FilesUtil.readBytes(attachment.getData());
			if (attachmentData != null)
				this.data = Base64.encodeToString(attachmentData);
		}
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
}
