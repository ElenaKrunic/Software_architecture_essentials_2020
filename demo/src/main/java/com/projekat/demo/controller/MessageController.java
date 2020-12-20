package com.projekat.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

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

import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.MessageServiceInterface;
import com.projekat.demo.service.UserService;

/**
 * Problem: ne ucitava mi se account kao json objekat u porukama 
 * @author lenovo
 *
 */
@RestController
@RequestMapping("api/messages")
public class MessageController {
	
	@Autowired
	private MessageServiceInterface messageService; 
	
	@GetMapping
	public ResponseEntity<List<MMessageDTO>> getMessages() {
		
		List<MMessage> messages = messageService.findAll(); 
		
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
		
		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MMessageDTO> getMessage(@PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id);
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND); 
		} else {
			return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK); 
		}
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<MMessageDTO> saveMessage(@RequestBody MMessageDTO messageDTO) {
		MMessage message = new MMessage(); 
		/*
		 * I ovde se trebaju postaviti objekti koji su clanovi objekta, liste itd.. 
		 * to,bcc,subject,cc - oni su EmailDTO
		 */
		//message.setBcc(messageDTO.getBcc());
		
		message.setContent(messageDTO.getContent());
		message.setSubject(messageDTO.getSubject());
		message.setDateTime(messageDTO.getDateTime());
		
		messageService.save(message); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.CREATED); 
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<MMessageDTO> updateMessage(@RequestBody MMessageDTO messageDTO, @PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id); 
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		message.setContent(messageDTO.getContent());
		message.setSubject(messageDTO.getSubject());
		message.setDateTime(messageDTO.getDateTime());
		
		messageService.save(message); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK); 
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id); 
		
		if(message != null) {
			messageService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

}
