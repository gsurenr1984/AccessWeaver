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
@Table(name="SIT_ASSET_USERS")
public class SitAssetUserDAO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long  assetId;
	private Long  userProfileId;
	private String  role;
	private boolean  readAccess;
	private boolean writeAccess;
	private String  createdBy;
	private Date createdDate;
	private String  updatedBy;
	private Date updatedDate;
	private String  status;
	private boolean approvalNeed;
}
