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
@Table(name="SIT_ASSET_SUB_TYPE")
public class SitAssetSubTypeDAO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long sitAssetTypeId;
	private String name;
	private String template;
	private boolean active;
	private String createdBy;
	private Date createdDate;
}
