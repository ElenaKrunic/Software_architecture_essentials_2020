package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.repository.TagRepository;


@Service
public class TagService implements TagServiceInterface {
	
    private static final Logger LOGGER = LogManager.getLogger(TagService.class);

    
	@Autowired
	private TagRepository tagRepository;

	@Override
	public Tag findOne(Integer id, User user) {
		LOGGER.info("Metoda iz servisa za pronalazenje jednog taga"); 
		return tagRepository.findByIdAndUser(id, user);
	}

	@Override
	public List<Tag> findAll() {
		LOGGER.info("Metoda iz servisa za pronalazenje svih tagova"); 
		return tagRepository.findAll();
	}

	@Override
	public Tag save(Tag tag) {
		LOGGER.info("Metoda iz servisa za cuvanje jednog taga"); 
		return tagRepository.save(tag);
	}

	public List<Tag> findByMessage(MMessage message) {
		LOGGER.info("Metoda iz servisa za pronalazenje jednog taga preko poruke"); 
		return tagRepository.findByMessages(message);
	}


	@Override
	public Tag findOne(Long tagId) {
		return tagRepository.getOne(tagId); 
	}

	@Override
	public List<Tag> findByUser(User user) {
		LOGGER.info("Metoda iz servisa za pronalazenje jednog taga preko korisnika"); 
		return tagRepository.findByUser(user);
	}

	@Override
	public void remove(Long tagId) {
		LOGGER.info("Metoda iz servisa za uklanjanje taga"); 
		tagRepository.deleteById(tagId);
	}

	public Tag findById(Integer id) {
		return tagRepository.findById(id);
	}

}
