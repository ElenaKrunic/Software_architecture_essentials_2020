package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AttachmentDTO;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.service.AttachmentServiceInterface;

@RestController
@RequestMapping(value="api/attachments")
public class AttachmentController {

	@Autowired
	private AttachmentServiceInterface attachmentService; 
	
	@GetMapping
	public ResponseEntity<List<AttachmentDTO>> getAttachments() {
		List<Attachment> attachments = attachmentService.findAll();
		List<AttachmentDTO> attachmentsDTO = new ArrayList<AttachmentDTO>();
		
		for(Attachment attachment : attachments) {
			attachmentsDTO.add(new AttachmentDTO(attachment));
		}
		return new ResponseEntity<List<AttachmentDTO>>(attachmentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.findOne(id); 
		
		if(attachment == null) {
			return new ResponseEntity<AttachmentDTO>(HttpStatus.BAD_REQUEST); 
		} else {
			return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.OK);
		}
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<AttachmentDTO> saveAttachment(@RequestBody AttachmentDTO attachmentDTO) {
		
		Attachment attachment = new Attachment(); 
		attachment.setData(attachmentDTO.getData());
		attachment.setMessage(attachment.getMessage());
		attachment.setMimeType(attachmentDTO.getMimeType());
		attachment.setName(attachmentDTO.getName());
		
		attachment = attachmentService.save(attachment);
		
		return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.CREATED); 
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentDTO attachmentDTO, @PathVariable("id") Integer id) {
		
		Attachment attachment = attachmentService.findOne(id); 
		
		if(attachment == null) {
			return new ResponseEntity<AttachmentDTO>(HttpStatus.BAD_REQUEST);
		}
		
		attachment.setData(attachmentDTO.getData());
		attachment.setMessage(attachment.getMessage());
		attachment.setMimeType(attachmentDTO.getMimeType());
		attachment.setName(attachmentDTO.getName());
	
		attachmentService.save(attachment);
		
		return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteAttachment(@PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.findOne(id); 
		
		if(attachment!=null) {
			attachmentService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
