package com.sitha.accessweaver;

import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class PasswordHasher {
	 /* public static void main(String[] args) {
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String hashed = encoder.encode("rsa123");
	        System.out.println(hashed);
	    }*/
	  
	  public static void main(String[] args) {
	        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
	        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
	        System.out.println("Generated JWT Secret: " + base64Key);
	    }


}
