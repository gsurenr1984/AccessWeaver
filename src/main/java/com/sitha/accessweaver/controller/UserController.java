package com.sitha.accessweaver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitha.accessweaver.dto.UserRequest;
import com.sitha.accessweaver.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final CustomUserDetailsService userDetailService;
	public UserController(CustomUserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(UserRequest user){
		
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
		if(userDetails != null) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		return null;		
	}
}
