package com.sitha.common.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRequest {
	private Long id;
	private Long userId;
	private String userName;
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
    private String password;
    
    //Incase of Business Account
    private boolean isBusinessAccount;
    private String comName;
    private String comEmail;
    private String comPhoneNumber;
    private String notes;
    private Long comId;
}
