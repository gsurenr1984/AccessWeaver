package com.sitha.accessweaver.dto;

import lombok.Data;

@Data
public class JwtResponse {
	public JwtResponse(String token) {
		this.token = token;
	}
	private String token;

}
