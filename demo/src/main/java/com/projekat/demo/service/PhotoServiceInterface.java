
package com.projekat.demo.service;

import java.util.List;

import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Photo;

public interface PhotoServiceInterface {
	
	public List<Photo> findAll();
	
	public List<Photo> getPhotos(Contact contact);


}
