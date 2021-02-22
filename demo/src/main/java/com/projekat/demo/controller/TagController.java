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

import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.dto.TagDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.ContactService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.TagService;
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping(value="api/tags")
public class TagController {
	
    private static final Logger LOGGER = LogManager.getLogger(TagController.class);

	@Autowired
	TagService tagService; 
	
	@Autowired
	AccountService accountService; 
	
	@Autowired
	MessageService messageService; 
	
	@Autowired
	ContactService contactService; 
	
	@Autowired
	UserService userService; 
	
	@GetMapping(value="/{id}")
	public ResponseEntity<TagDTO> getTag(@PathVariable("id") Integer id) {
		Tag tag = tagService.findById(id); 
		
		if(tag==null) {
			return new ResponseEntity<TagDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
	}
	
	@PostMapping(value="/{id}",consumes="application/json")
	public ResponseEntity<TagDTO> saveTag(@RequestBody TagDTO tagDTO, @PathVariable("id") Integer id) {
		User user = userService.findOne(id); 
		
		if(user == null) {
			return new ResponseEntity<TagDTO>(HttpStatus.NOT_FOUND);
		}
		
		Tag tag = new Tag(); 
		tag.setName(tagDTO.getName());
		
		user.addTag(tag);
		
		tag = tagService.save(tag);
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/updateTag/{id}", consumes = "application/json")
	public ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tagDTO, @PathVariable("id") Integer id){
		
		//User user = userService.findOne()
		Tag tag = tagService.findById(id);
		
		if(tag == null) {
			return new ResponseEntity<TagDTO>(HttpStatus.NOT_FOUND); 
		}
		
		tag.setName(tagDTO.getName());
		
		tag = tagService.save(tag); 
		
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="deleteTag/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
		
		Tag tag = tagService.findOne(id); 
		
		if(tag == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); 
		} else {
			tagService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
		
	@GetMapping(value="/{id}/{accountId}/messages", consumes="application/json")
	public ResponseEntity<List<MMessageDTO>> getByTagMessages(@PathVariable("accountId") Integer accountId, @PathVariable("id") Integer id) {
		
		Tag tag = tagService.findById(id); 
		
		if(tag==null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		Account account = accountService.findOne(accountId); 
		if(account == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = messageService.findByAccountAndTag(account,tag);
		List<MMessageDTO> messagesDTO = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			messagesDTO.add(new MMessageDTO(message));
		}
		
		return new ResponseEntity<List<MMessageDTO>>(messagesDTO, HttpStatus.OK);
		
	}
}
