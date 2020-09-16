package com.projekat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;


@Entity
@Table(name="photos")
public class Photo {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Lob
	@Column(name="path", nullable=true)
	private byte[] path;
	
	@ManyToOne
	@JoinColumn(name="contact", referencedColumnName = "id", nullable=false)
	private Contact contact;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getPath() {
		return path;
	}

	public void setPath(byte[] path) {
		this.path = path;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Photo(Integer id, byte[] path, Contact contact) {
		super();
		this.id = id;
		this.path = path;
		this.contact = contact;
	}

	public Photo() {
		super();
	} 
}
