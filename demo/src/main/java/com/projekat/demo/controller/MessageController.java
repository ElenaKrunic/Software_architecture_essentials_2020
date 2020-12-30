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

import com.projekat.demo.dto.AttachmentDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.dto.TagDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AttachmentService;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.MessageServiceInterface;
import com.projekat.demo.service.TagService;
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
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private AttachmentService attachmentService; 
	
	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private MailAPI mailApi;
	
	/**
	 * 
	 * @return vraca sve poruke iz baze 
	 */
	@GetMapping
	public ResponseEntity<List<MMessageDTO>> getMessages() {
		
		List<MMessage> messages = messageService.findAll(); 
		
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
		
		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	/**
	 * 
	 * @param id proslijedjen id poruke 
	 * @return vraca poruku koja ima odredjeni ID 
	 */
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MMessageDTO> getMessage(@PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id);
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND); 
		} else {
			return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK); 
		}
	}
	
	/**
	 * 
	 * @param id taga
	 * @return vraca tag za koji smo unijeli ID i vraca sve podatke o user-u koji ima account ... 
	 */
	
	@GetMapping(value="/{id}/tags")
	public ResponseEntity<List<TagDTO>> getMessageTags(@PathVariable("id") Integer id) {
		
		MMessage message = messageService.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<TagDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Tag> tags = tagService.findByMessage(message);
		List<TagDTO> tagsDto = new ArrayList<TagDTO>(); 
		
		for(Tag tag: tags) {
			tagsDto.add(new TagDTO(tag));
		}
		
		return new ResponseEntity<List<TagDTO>>(tagsDto, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/attachments")
	public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable("id") Integer id) {
		
		MMessage message = messageService.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<AttachmentDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Attachment> attachments = attachmentService.findByMessage(message);
		List<AttachmentDTO> messageAttachmentsDto = new ArrayList<AttachmentDTO>();
		
		for(Attachment attachment: attachments) {
			AttachmentDTO attachmentDTO = new AttachmentDTO(attachment);
			attachmentDTO.setData(null);
			messageAttachmentsDto.add(attachmentDTO);
		}
		
		return new ResponseEntity<List<AttachmentDTO>>(messageAttachmentsDto, HttpStatus.OK);
	}
	

	
	/*
	@PostMapping(consumes="application/json")
	public ResponseEntity<MMessageDTO> saveMessage(@RequestBody MMessageDTO messageDTO) {
		MMessage message = new MMessage(); 
		/*
		 * I ovde se trebaju postaviti objekti koji su clanovi objekta, liste itd.. 
		 * to,bcc,subject,cc - oni su EmailDTO
		 */
		//message.setBcc(messageDTO.getBcc());

	/*	message.setContent(messageDTO.getContent());
		message.setSubject(messageDTO.getSubject());
		message.setDateTime(messageDTO.getDateTime());
		
		messageService.save(message); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.CREATED); 
	}
	*/
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

	//========================= Kreiranje nove poruke u folderu =========================================================
	
	@PostMapping(value="/{accountId}", consumes="application/json")
	public ResponseEntity<MMessageDTO> saveMessage(@RequestBody MMessageDTO messageDTO, @PathVariable("accountId") Integer id) {
	
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		MMessage message = new MMessage(); 
		message.setFrom(messageDTO.getFrom());
		message.setTo(messageDTO.getTo());
		message.setCc(messageDTO.getCc());
		message.setBcc(messageDTO.getBcc());
		message.setDateTime(messageDTO.getDateTime());
		message.setSubject(messageDTO.getSubject());
		message.setContent(messageDTO.getContent());
		message.setUnread(false);
		
		account.addMessage(message);
		addMessageInSentFolder(message,account); 
		
		account = accountService.save(account);
		
		if(account == null) {
			System.out.println("Account je null");
		}
		
		message = messageService.save(message);
		
		boolean sent = mailApi.sendMessage(message); 
		//System.out.println("Ovo je sadrzaj sent poruke koja odlazi na API" +sent );
		
		if(sent) {
			message = messageService.save(message);
			System.out.println("Ovo je poruka koja se vratila kao true ili false sa mail api-ja" + message);
			
			return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.CREATED);
		}
		return new ResponseEntity<MMessageDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void addMessageInSentFolder(MMessage message, Account account) {
		for(Folder folder : account.getFolders()) {
			if(folder.getName().equalsIgnoreCase("Sent")) {
				folder.addMessage(message);
				break; 
			}
		}
	}
}
