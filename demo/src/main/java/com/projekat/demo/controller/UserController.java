package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.dto.UserDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Contact;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.service.UserService;
import com.projekat.demo.service.UserServiceInterface;

import javassist.expr.NewArray;

@RestController
@RequestMapping("api/users")
public class UserController {

	//get all users 
	//get users 
	
	@Autowired
	private UserService userService; 
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<User> users = userService.findAll(); 
		
		List<UserDTO> dtoUsers = new ArrayList<UserDTO>();
		
		for(User user : users) {
			dtoUsers.add(new UserDTO(user)); 
		}
		return new ResponseEntity<List<UserDTO>>(dtoUsers, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
		User user = userService.findOne(id); 
		
		if(user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK); 
	}
	
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
	
	@PostMapping("/login")
	public ResponseEntity<?> ulogujKorisnika(@RequestBody UserDTO userDTO) {
		
	User existsUser = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
	
	if(existsUser != null) {
		return new ResponseEntity<UserDTO>(HttpStatus.OK);
	} else {
		System.out.println("Ne postoji korisnik sa datim imenom i sifrom!"); 
	}
	return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
}
	
}
