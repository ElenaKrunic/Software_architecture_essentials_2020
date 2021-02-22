package com.projekat.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.Aplikacija;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.repository.AttachmentRepository;

@Service
public class AttachmentService implements AttachmentServiceInterface {

    private static final Logger LOGGER = LogManager.getLogger(AttachmentService.class);

	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Override
	public List<Attachment> findAll() {
		LOGGER.info("Metoda u servisu za pronalazak svih priloga"); 
		return attachmentRepository.findAll();
	}

	@Override
	public Attachment save(Attachment attachment) {
		LOGGER.info("Metoda u servisu za cuvanje priloga"); 
		return attachmentRepository.save(attachment);
	}

	public List<Attachment> findByMessage(MMessage message) {
		LOGGER.info("Metoda u servisu za prolanazenje priloga pomocu poruke"); 
		return attachmentRepository.findByMessage(message);
	}

	@Override
	public Attachment findOne(Integer id) {
		LOGGER.info("Metoda u prilogu za pronalazenje priloga po ID-u"); 
		return attachmentRepository.findById(id);
	}

	@Override
	public void remove(Attachment attachment)  {
		LOGGER.info("Metoda za uklanjanje priloga"); 
		attachmentRepository.delete(attachment);
	}

}
