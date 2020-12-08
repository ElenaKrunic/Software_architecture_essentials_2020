package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;



public interface TagServiceInterface {

public Tag findOne(Integer id, User user);
	
	public List<Tag> findAll();
	
	public Tag save(Tag tag);
	
	public void remove(Integer id);
}
