package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Attachment;


public interface AttachmentServiceInterface {
	
public Attachment findOne(Integer id);
	
	public List<Attachment> findAll();
	
	public Attachment save(Attachment attachment);
	
	public void remove(Integer id);

}
