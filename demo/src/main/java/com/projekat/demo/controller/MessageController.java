package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping("account/{index}/messages")
public class MessageController {
	
	/*

	@Autowired
	private Send_Pull_Mails_API mailApi;
	
	@Autowired
	private MessageService messageService; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private FolderService folderService; 
	
	@Autowired
	private AccountService accountService; 
	
	
	@GetMapping
	public ResponseEntity<List<MMessageDTO>> getAllMessages(@PathVariable("id") Integer accountId) {
		
		//pronalazim nalog po id-u
		Account account = accountService.findOne(accountId);
		
		if(account == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.BAD_REQUEST);
		} else {
			//na osnovu naloga pronalazim listu poruka za taj nalog 
			List<MMessage> messageEntities = messageService.findAllMessagesByAccount(account);
			
			List<MMessageDTO> messagesForAnAccount = new ArrayList<MMessageDTO>(); 
			
			for(MMessage message: messageEntities) {
				MMessageDTO dto = new MMessageDTO(message);
				messagesForAnAccount.add(dto);
			}
			return new ResponseEntity<List<MMessageDTO>>(messagesForAnAccount,HttpStatus.OK);
		}
	} */
}
