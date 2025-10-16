package com.sitha.accessweaver.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sitha.accessweaver.dto.JwtResponse;
import com.sitha.accessweaver.dto.LoginRequest;
import com.sitha.accessweaver.dto.TokenRequest;
import com.sitha.accessweaver.util.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
	
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
	
	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.authenticationManager =authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	
	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
	
	@PostMapping("/auth/validate")
    public ResponseEntity<?> validateToken(@RequestBody TokenRequest request) {
        try {
            boolean tokenValidated = jwtUtils.validateJwtToken(request.getToken());
            return ResponseEntity.ok(Map.of("valid", tokenValidated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
        }
    }

	
	@PostMapping("/app-logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            // TODO Surendra Optionally add jwt to blacklist or invalidate logic
        }
        return ResponseEntity.ok("Logged out successfully");
    }


}
