package com.sitha.accessweaver.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private final String jwtSecret = "DZgTkYhmEhtwk8lsodcre/n3Jq6DfqTR/pzey8td9Xc=";
    private final int jwtExpirationMs = 86400000; // 1 day

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    
	public String generateJwtToken(UserDetails userDetails) {

		 return Jwts.builder()
		            .subject(userDetails.getUsername())
		            .issuedAt(new Date())
		            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
		            .signWith(getSigningKey(), Jwts.SIG.HS256)
		            .compact();

    }

    public String getUsernameFromJwtToken(String token) {
    	return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public boolean validateJwtToken(String token) {
    	try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }

    }

}
