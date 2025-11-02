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
@Table(name="SIT_ORG")
public class OrganizationDAO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orgName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String pincode;
	private String email;
	private String phoneNumber;
	private Long createdBy;
    private Long updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private String notes;
    private boolean isActive;
}
