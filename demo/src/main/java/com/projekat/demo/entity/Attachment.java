package com.projekat.demo.entity;

import java.io.Serializable;

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
public class Attachment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "data", columnDefinition = "LONGTEXT", nullable = false)
	private String data;

	@Column(name = "mime_type", columnDefinition = "VARCHAR(20)", nullable = false)
	private String mimeType;

	@Column(name = "name", columnDefinition = "VARCHAR(250)", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "message", referencedColumnName = "id", nullable = true)
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

	public static Attachment clone(Attachment attachment) {
		Attachment copy = new Attachment();
		copy.setName(attachment.getName());
		copy.setMimeType(attachment.getMimeType());
		copy.setData(attachment.getData());
		return copy;
	}

}
