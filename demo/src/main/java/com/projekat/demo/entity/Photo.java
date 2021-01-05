package com.projekat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

import javax.persistence.Column;

@Entity
@Table(name="photos")
public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1552222911104137525L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "path", nullable = false)
	private String path;

	//@OneToOne(mappedBy = "plate", cascade = CascadeType.ALL, orphanRemoval = true)
	//@JsonIgnore
	//@OneToOne(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
	//@JsonBackReference
	//private Contact contact;
	@OneToOne(mappedBy="photo")
	private Contact contact; 
	
	public Photo() {
		
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
