package com.projekat.demo.dto;

import java.util.List;

import com.projekat.demo.entity.Tag;

public class TagDTO {

	private Integer id; 
	private String name; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public TagDTO(Integer id, String name, List<MMessageDTO> messages, UserDTO user) {
		super();
		this.id = id;
		this.name = name;
	}
	public TagDTO() {
		super();
	}
	
	//transformacija entiteta u dtobean: 
	public TagDTO(Tag tag){
		this.id=tag.getId();
		this.name=tag.getName();
	}
}
