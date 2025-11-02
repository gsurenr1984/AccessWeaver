package com.sitha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
	  public static void main(String[] args) {
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String hashed = encoder.encode("rsa123");
	        System.out.println(hashed);
	    }
	  
	  /*public static void main(String[] args) {
	        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
	        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
	        System.out.println("Generated JWT Secret: " + base64Key);
	    }*/


}
