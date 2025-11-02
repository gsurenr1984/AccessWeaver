package com.sitha.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sitha.accessweaver.entity.User;
import com.sitha.common.dao.OrganizationDAO;
import com.sitha.common.dao.UserDetail;
import com.sitha.common.dto.UserRequest;
import com.sitha.common.repository.OrganizationRepository;
import com.sitha.common.repository.UserProfileRepository;
import com.sitha.common.repository.UserRepository;

@Service
public class UserManagementService {
	private final UserProfileRepository userDetailService;
	private final UserRepository userRepository;
	private final OrganizationRepository orgRepo;
	private final PasswordEncoder encoder;
	
	public UserManagementService(UserProfileRepository userDetailService, UserRepository userRepository, PasswordEncoder encoder, OrganizationRepository orgRepo) {
		this.userDetailService = userDetailService;
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.orgRepo = orgRepo;
	}
	
	public UserDetail getUserProfileDetails(UserRequest user) {
		Optional<UserDetail> userDetail = userDetailService.findByUniqueUser(user.getUserName(), user.getEmail(), user.getPhoneNumber());
		if(userDetail.isPresent()) {
			return userDetail.get();
		}
		
		return null;
	}
	
	public UserDetail saveUserProfile(UserDetail userRequest) {
		UserDetail user = userDetailService.save(userRequest);
		
		return user;
	}
	
	public User saveUser(UserRequest userDetail) {
		User user = new User();
		user.setUsername(userDetail.getUserName());
		user.setPassword(encoder.encode(userDetail.getPassword()));
		user.setRoles("Admin");
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public Long saveOrganization(UserRequest userDetail) {
		OrganizationDAO org = new OrganizationDAO();
		org.setActive(true);
		org.setAddressLine1(userDetail.getFirstLineAddress());
		org.setAddressLine2(userDetail.getSecondLineAddress());
		org.setCity(userDetail.getCity());
		org.setState(userDetail.getState());
		org.setPincode(userDetail.getPincode());
		
		org.setCreatedBy(userDetail.getUserId());
		org.setEmail(userDetail.getComEmail());
		org.setNotes(userDetail.getNotes());
		org.setOrgName(userDetail.getComName());
		org.setPhoneNumber(userDetail.getComPhoneNumber());
		org = orgRepo.save(org);
		return org.getId();
	}
}
