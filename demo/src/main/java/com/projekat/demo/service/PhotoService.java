package com.projekat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;
import com.projekat.demo.repository.PhotoRepository;

@Service
public class PhotoService implements PhotoServiceInterface {
	
	//slika za kontakt 
	@Autowired
	private PhotoRepository photoRepository;

	@Override
	public List<Photo> findAll() {
		return photoRepository.findAll();
	}

	@Override
	public List<Photo> getPhotos(Contact contact) {
		return photoRepository.findByContact(contact);
	}
	
	public Photo getOne(Integer photoId) {
		return photoRepository.getOne(photoId);
	}
	
}
