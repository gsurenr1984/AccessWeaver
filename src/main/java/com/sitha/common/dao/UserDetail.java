package com.sitha.common.dao;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="SIT_USER_PROFILE")
public class UserDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private String username;
	private String fName;
	private String lName;
	private String mName;
	private String email;
	private String phoneNumber;
	private String firstLineAddress;
	private String secondLineAddress;
	private String city;
	private String state;
	private String pincode;
    private String proofOfIdentityType;
    private String proofOfIdentityNbr;
    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private boolean isActive;
    
    private Long orgId;
}
