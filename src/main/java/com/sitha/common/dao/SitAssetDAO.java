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
@Table(name="SIT_ASSET")
public class SitAssetDAO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long parentAssetId;
	private Long assetTypeId;
	private String assetSubType;
	private String assetNumber;
	private String assetName;
	private String assetDescription;
	private String assetAddress;
	
	private String status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
    private Date updatedDate;
    private boolean businessInd;
    private String assetProperties;
}
