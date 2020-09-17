package com.projekat.demo.dto;

import com.projekat.demo.entity.Photo;

public class PhotoDTO {
	
	private Integer id;
	private String path;
	
	public PhotoDTO(Photo photo) {
		this.id= photo.getId();
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
	public PhotoDTO(Integer id, String path) {
		super();
		this.id = id;
		this.path = path;
	} 
	
	public PhotoDTO() {
		
	}
}
