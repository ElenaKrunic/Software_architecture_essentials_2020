package com.projekat.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.UserDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.service.UserService;

import javassist.expr.NewArray;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService; 
	
	@PostMapping("/registration")
	public ResponseEntity<?> sacuvajUbazu(@RequestBody UserDTO userDTO) {
		//validacija 
		if(userDTO.getFirstName() == null) {
			return new ResponseEntity<String>("Morate unijeti ime", HttpStatus.BAD_REQUEST);
		}
		if(userDTO.getLastName() == null) {
			return new ResponseEntity<String>("Morate unijeti prezime za korisnika", HttpStatus.BAD_REQUEST);
		}
		if(userDTO.getPassword() == null) {
			return new ResponseEntity<String>("Morate unijeti sifru za korisnika", HttpStatus.BAD_REQUEST);
		} 
		if(userDTO.getUsername() == null) {
			return new ResponseEntity<String>("Morate unijeti korisnicko ime", HttpStatus.BAD_REQUEST);
		}
		
		User korisnickoImeVecPostoji = userService.findByUsername(userDTO.getUsername());
		if(korisnickoImeVecPostoji != null) {
			return new ResponseEntity<String>("Korisnicko ime je vec u upotrebi", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(); 
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setAccounts(new ArrayList<Account>());
		user.setContacts(new ArrayList<Contact>());
		user.setTags(new ArrayList<Tag>());
		
		user = userService.save(user); 
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);

	}
}
