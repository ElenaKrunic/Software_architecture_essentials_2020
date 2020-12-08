package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.TagRepository;



public class TagService implements TagServiceInterface {
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public Tag findOne(Integer id, User user) {
		return tagRepository.findByIdAndUser(id, user);
	}

	@Override
	public List<Tag> findAll() {
		return tagRepository.findAll();
	}

	@Override
	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public void remove(Integer id) {
		tagRepository.deleteById(id);

	}

}
