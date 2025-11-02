package com.sitha.common.dto;

import lombok.Data;

@Data
public class EntityRequest {
	private Long id;
	private String name;
	private String description;
	private boolean active;
	private Long assetTypeId;
}
