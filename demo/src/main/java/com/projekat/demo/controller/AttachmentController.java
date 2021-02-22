package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.service.AttachmentServiceInterface;
import com.projekat.demo.service.MessageService;

/**
 * Kontroler za rad sa prilozima 
 * Implementirane su osnovne CRUD metode 
 * 
 * @author Elena Krunic 
 *
 */
@RestController
@RequestMapping(value="api/attachments")
public class AttachmentController {

    private static final Logger LOGGER = LogManager.getLogger(AttachmentController.class);

	@Autowired
	private AttachmentServiceInterface attachmentService; 
	
	@Autowired
	private MessageService messageService; 
	
	/**
	 * 
	 * @return lista priloga iz baze 
	 */
	@GetMapping
	public ResponseEntity<List<AttachmentDTO>> getAttachments() {
		List<Attachment> attachments = attachmentService.findAll();
		List<AttachmentDTO> attachmentsDTO = new ArrayList<AttachmentDTO>();
		
		for(Attachment attachment : attachments) {
			attachmentsDTO.add(new AttachmentDTO(attachment));
		}
		
		LOGGER.info("Uspjesno vracena lista priloga"); 
		return new ResponseEntity<List<AttachmentDTO>>(attachmentsDTO, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id priloga
	 * @return prilog iz baze ciji je ID proslijedjen 
	 */
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.findOne(id);
		
		if(attachment == null) {
			return new ResponseEntity<AttachmentDTO>(HttpStatus.NOT_FOUND);
		}
		
		LOGGER.info("Uspjesno vracen jedan prilog"); 

		return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param attachmentDTO proslijedjen je prilog koji cemo da kreiramo 
	 * @param id poruke unutar koje cemo da sacuvamo prilog 
	 * @return kreirani prilog u bazi 
	 */
	@PostMapping(value="addAttachmentForMessage/{id}", consumes="application/json")
	public ResponseEntity<AttachmentDTO> saveAttachment(@RequestBody AttachmentDTO attachmentDTO,@PathVariable("id") Integer id) {
		
		MMessage message = messageService.findOne(id);
		
		if(message == null) {
			return new ResponseEntity<AttachmentDTO>(HttpStatus.NOT_FOUND);
		}
		
		Attachment attachment = new Attachment(); 
		attachment.setData(attachmentDTO.getData());
		attachment.setMimeType(attachmentDTO.getMimeType());
		attachment.setName(attachmentDTO.getName());
		message.addAttachment(attachment);
		
		attachment = attachmentService.save(attachment);
		
		LOGGER.info("Uspjesno sacuvan novi prilog"); 

		return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.CREATED); 
	}
	
	/**
	 * 
	 * @param attachmentDTO tijelo priloga koje cemo da mijenjamo 
	 * @param id postojeceg priloga 
	 * @return izmijenjen prilog i sacuvan u bazu 
	 */
	@PutMapping(value="updateAttachment/{id}", consumes = "application/json")
	public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentDTO attachmentDTO, @PathVariable("id") Integer id) {

		Attachment attachment = attachmentService.findOne(id); 
				
		if(attachment == null) {
			return new ResponseEntity<AttachmentDTO>(HttpStatus.NOT_FOUND);
		}
		
		
		attachment.setData(attachmentDTO.getData());
		attachment.setMimeType(attachmentDTO.getMimeType());
		attachment.setName(attachmentDTO.getName());
	
		attachment = this.attachmentService.save(attachment);
		
		LOGGER.info("Uspjesno odradjena izmjena priloga"); 

		return new ResponseEntity<AttachmentDTO>(new AttachmentDTO(attachment), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id priloga kog cemo brisati 
	 * @return obrisan prilog iz baze 
	 */
	@DeleteMapping(value="deleteAtt/{id}")
	public ResponseEntity<Void> deleteAttachment(@PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.findOne(id); 
		
		if(attachment!=null) {
			attachmentService.remove(attachment);
			LOGGER.info("Uspjesno odradjeno brisanje priloga"); 
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
