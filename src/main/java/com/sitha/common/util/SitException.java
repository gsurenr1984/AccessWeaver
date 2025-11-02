package com.sitha.common.util;

import lombok.Data;

@Data
public class SitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public SitException(String message) {
		super(message);
		this.message = message;
	}

}
