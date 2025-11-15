package com.sitha.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitha.common.dao.UserDetail;
import com.sitha.common.dto.UserRequest;
import com.sitha.common.util.SitUtil;
import com.sitha.user.service.OTPService;
import com.sitha.user.service.UserManagementService;

@RestController
@RequestMapping("/api/otp")
public class OTPControler {
	
private static final Logger logger = LoggerFactory.getLogger(OTPControler.class);

	
	private final UserManagementService umService;
	private final OTPService otpService;

	public OTPControler(UserManagementService umService, OTPService otpService) {
		this.umService = umService;
		this.otpService = otpService;
	}
	
	@GetMapping()
	public ResponseEntity<?> sendOtp( @RequestParam String input, @RequestParam(required=false) boolean isExistingUser) {
		
		if (!StringUtils.hasLength(input)) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		if(isExistingUser) {
			UserDetail updatedUser = umService.findUserWithInputField(input);
			if(updatedUser == null || !StringUtils.hasLength(updatedUser.getEmail())) {
				return ResponseEntity.badRequest().body("Invalid User Name");
			}
			input = updatedUser.getEmail();
		} 
		
		if(SitUtil.isValidEmail(input)) {
			otpService.generateOtp(input);
			return ResponseEntity.ok().body("Otp Sent Successfully");
		}
		
	
		return ResponseEntity.internalServerError().build();
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyOtp(@RequestBody UserRequest user) {
		String source = null;
		logger.info("Verify OTP in Controller, Email/source={}, OTP={}", user.getUserName(), user.getOtp());
		if(StringUtils.hasLength(user.getUserName()) || StringUtils.hasLength(user.getEmail()) || StringUtils.hasLength(user.getPhoneNumber())) {
			UserDetail updatedUser = umService.getUserProfileDetails(user);
			if (updatedUser != null && StringUtils.hasLength(updatedUser.getEmail())) {
				source = updatedUser.getEmail();
			} else if(StringUtils.hasLength(user.getUserName())) {
				source = user.getUserName();
			}else if(StringUtils.hasLength(user.getEmail())) {
				source = user.getEmail();
			} else if(StringUtils.hasLength(user.getPhoneNumber())) {
				source = user.getPhoneNumber();
			}
			
			
		}
		
		
		if(StringUtils.hasLength(source) && otpService.verifyOtp(source, user.getOtp())) {
			return ResponseEntity.ok("OTP Verified");
		}
		return ResponseEntity.badRequest().body("ENtered OTP is not matching");
	}

}
