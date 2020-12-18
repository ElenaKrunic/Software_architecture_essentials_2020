package com.projekat.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.mail.Send_Pull_Mails_API;
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

}
