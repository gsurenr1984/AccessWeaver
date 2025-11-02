package com.sitha.common.dto;

import lombok.Data;

@Data
public class AssetRequest {
	private Long id;
	private Long parentAssetId;
	private Long assetTypeId;
	private String assetSubType;
	private String assetNumber;
	private String assetName;
	private String assetDescription;
	private String assetAddress;
	private String operatorId;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private String status;
	private String assetProperties;
	private String name;
	private String description;
	private boolean active;
}
