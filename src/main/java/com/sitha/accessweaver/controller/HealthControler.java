package com.sitha.accessweaver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControler {
	
	@GetMapping("/health")
	public String checkHealth() {
		return "Access Weaver renning successfully";
	}
	
	@GetMapping("/register")
	public String checkregister() {
		return "Access Weaver register successfully";
	}
}
