package com.projekat.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="attachments")
public class Attachment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Column(name="data", nullable=false)
	private String data; 
	
	@Column(name="mimeType", nullable=false)
	private String mimeType; 
	
	@Column(name="name", nullable=false)
	private String name; 
	
	//veza sa porukama many to one 
	@ManyToOne
	@JoinColumn(name="message", referencedColumnName = "id", nullable=false)
	private MMessage message;

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

	public MMessage getMessage() {
		return message;
	}

	public void setMessage(MMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", data=" + data + ", mimeType=" + mimeType + ", name=" + name + ", message="
				+ message + "]";
	}

	public Attachment(Integer id, String data, String mimeType, String name, MMessage message) {
		super();
		this.id = id;
		this.data = data;
		this.mimeType = mimeType;
		this.name = name;
		this.message = message;
	}

	public Attachment() {
		super();
	} 
	
	
}
