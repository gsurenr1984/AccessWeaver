package com.sitha.user.controller;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitha.accessweaver.entity.User;
import com.sitha.common.dao.UserDetail;
import com.sitha.common.dto.UserRequest;
import com.sitha.user.service.UserManagementService;

@RestController
@RequestMapping("/api/user")
public class UserManagementController {
	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	
	private final UserManagementService umService;

	public UserManagementController(UserManagementService umService) {
		this.umService = umService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerSelfUser(@RequestBody UserRequest userProfileDetail) {
		UserDetail userDetail = umService.getUserProfileDetails(userProfileDetail);
		if (userDetail != null && !StringUtils.hasLength(userDetail.getUsername())) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		User user = umService.saveUser(userProfileDetail);
		if(userProfileDetail.isBusinessAccount()) {
			Long orgId = umService.saveOrganization(userProfileDetail);
			userProfileDetail.setComId(orgId);
		}
		userDetail = prepareUserDetailsObj(userProfileDetail);
		userDetail.setUserId(user.getId());
		userDetail.setCreatedBy(userProfileDetail.getUserName());
		userDetail = umService.saveUserProfile(userDetail);
		return ResponseEntity.ok(userProfileDetail);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createUserByAnotherUser(@RequestBody UserRequest userProfileDetail) {
		UserDetail userDetail = umService.getUserProfileDetails(userProfileDetail);
		if (userDetail != null) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		userDetail = prepareUserDetailsObj(userProfileDetail);
		userDetail.setUsername(null);
		userDetail.setCreatedBy(userDetail.getUsername());
		userDetail = umService.saveUserProfile(userDetail);
		return ResponseEntity.ok(userDetail);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody UserRequest userProfileDetail) {
		if(StringUtils.hasLength(userProfileDetail.getUserName()) ||
				StringUtils.hasLength(userProfileDetail.getEmail()) ||
						StringUtils.hasLength(userProfileDetail.getPhoneNumber())) {
			UserDetail userDetail = umService.getUserProfileDetails(userProfileDetail);
			if (userDetail == null || !StringUtils.hasLength(userDetail.getUsername())) {
				return ResponseEntity.ok("Valid");
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestBody UserRequest user) {
		UserDetail updatedUser = umService.getUserProfileDetails(user);
		if (updatedUser == null) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		updatedUser.setActive(false);
		updatedUser.setUpdatedBy(user.getUserName());
		updatedUser.setUpdatedDate(Calendar.getInstance().getTime());
		updatedUser = umService.saveUserProfile(updatedUser);
		return ResponseEntity.ok(updatedUser);
	}

	private UserDetail prepareUserDetailsObj(UserRequest userProfileDetail) {
		UserDetail ud = new UserDetail();
		ud.setActive(userProfileDetail.isActive());
		ud.setCity(userProfileDetail.getCity());
		ud.setCreatedBy(userProfileDetail.getCreatedBy());
		ud.setCreatedDate(new Date());
		ud.setEmail(userProfileDetail.getEmail());
		ud.setFirstLineAddress(userProfileDetail.getFirstLineAddress());
		ud.setFName(userProfileDetail.getFName());
		ud.setLName(userProfileDetail.getLName());
		ud.setMName(userProfileDetail.getMName());
		ud.setPhoneNumber(userProfileDetail.getPhoneNumber());
		ud.setPincode(userProfileDetail.getPincode());
		ud.setProofOfIdentityNbr(userProfileDetail.getProofOfIdentityNbr());
		ud.setProofOfIdentityType(userProfileDetail.getProofOfIdentityType());
		ud.setSecondLineAddress(userProfileDetail.getSecondLineAddress());
		ud.setState(userProfileDetail.getState());
		ud.setUpdatedBy(userProfileDetail.getUpdatedBy());
		ud.setUsername(userProfileDetail.getUserName());
		
		ud.setOrgId(userProfileDetail.getComId());
		return ud;
	}
}
