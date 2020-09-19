package com.projekat.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private UserService userService; 
	
	
}
