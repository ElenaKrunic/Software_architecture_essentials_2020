package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
	
	public List<Photo> findAll();

	public List<Photo> findByContact(Contact contact);
}
