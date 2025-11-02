package com.sitha.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitha.accessweaver.util.JwtUtils;
import com.sitha.common.dao.SitAssetSubTypeDAO;
import com.sitha.common.dao.SitAssetTypeDAO;
import com.sitha.common.dto.EntityRequest;
import com.sitha.common.util.SitException;
import com.sitha.user.service.AssetTypesMgmtService;

@RestController
@RequestMapping("/api/asset")
public class AssetTypeMgmtController {
	private static final Logger logger = LoggerFactory.getLogger(AssetTypeMgmtController.class);
	private final AssetTypesMgmtService service;
	private final JwtUtils jwt;
	
	public AssetTypeMgmtController(AssetTypesMgmtService service, JwtUtils jwt) {
		this.service = service;
		this.jwt = jwt;
	}
	//================Asset Type Codes ===================================
	@GetMapping({"/type/{entityStatusInd}", "/type/"})
	public ResponseEntity<?> getAssets(@PathVariable(required = false) String entityStatusInd) {
		List<SitAssetTypeDAO> entities = new ArrayList<>();
		if(StringUtils.hasLength(entityStatusInd)) {
			if(entityStatusInd.equalsIgnoreCase("Active")) {
				entities.addAll(service.getActiveAssetTypes());
			} else {
				entities.addAll(service.getStatusAssetType(false));
			}
		} else {
			entities.addAll(service.getAsstTypes());
		}
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/type/create")
	public ResponseEntity<?> createAsset(@RequestBody EntityRequest entity,
			@AuthenticationPrincipal UserDetails userDetails) {
		List<SitAssetTypeDAO> existingEntities = service.getAssetTypeByName(entity.getName());
		if (existingEntities != null && !existingEntities.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		SitAssetTypeDAO savedRecord = null;
		try {
			savedRecord = service.createActiveAssetType(entity, userDetails.getUsername());
		} catch (SitException e) {
			logger.error("Exception occured in Entity creation");
		}
		
		return ResponseEntity.ok(savedRecord);
	}
	
	@PostMapping("/type/delete")
	public ResponseEntity<?> deleteEntity(@RequestBody EntityRequest entity, @AuthenticationPrincipal UserDetails userDetails) {
		List<SitAssetTypeDAO> existingEntities = service.getAssetTypeByName(entity.getName());
		if (existingEntities == null || existingEntities.isEmpty() ) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		} else if(existingEntities.size()>1) {
			return ResponseEntity.badRequest().body("More than one entity is exists, please contact admin");
		}
		boolean entityDeleted = false;
		try {
			entityDeleted = service.deleteAssetType(existingEntities.get(0), userDetails.getUsername());
		} catch (SitException e) {
			logger.error("Exception occured in Entity creation");
		}
		
		
		return ResponseEntity.ok(entityDeleted?"Entity deleted successfully":"Entity not deleted");
	}
	
	//============================AssetSubTypes=================================
	@GetMapping({"/sub-type/{entityStatusInd}", "/sub-type/"})
	public ResponseEntity<?> getSubAssets(@PathVariable(required = false) String entityStatusInd) {
		List<SitAssetSubTypeDAO> entities = new ArrayList<>();
		if(StringUtils.hasLength(entityStatusInd)) {
			if(entityStatusInd.equalsIgnoreCase("Active")) {
				entities.addAll(service.getActiveAssetSubTypes());
			} else {
				entities.addAll(service.getStatusAssetSubType(false));
			}
		} else {
			entities.addAll(service.getAssetSubTypes());
		}
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/sub-type/create")
	public ResponseEntity<?> createSubAsset(@RequestBody EntityRequest entity,
			@AuthenticationPrincipal UserDetails userDetails) {
		List<SitAssetSubTypeDAO> existingEntities = service.getAssetSubTypeByName(entity.getName());
		if (existingEntities != null && !existingEntities.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		}
		SitAssetTypeDAO savedRecord = null;
		try {
			savedRecord = service.createActiveAssetType(entity, userDetails.getUsername());
		} catch (SitException e) {
			logger.error("Exception occured in Entity creation");
		}
		
		return ResponseEntity.ok(savedRecord);
	}
	
	@PostMapping("/sub-type/delete")
	public ResponseEntity<?> deleteAssetSubType(@RequestBody EntityRequest entity, @AuthenticationPrincipal UserDetails userDetails) {
		List<SitAssetSubTypeDAO> existingEntities = service.getAssetSubTypeByName(entity.getName());
		if (existingEntities == null || existingEntities.isEmpty() ) {
			return ResponseEntity.badRequest().body("Invalid User Name");
		} else if(existingEntities.size()>1) {
			return ResponseEntity.badRequest().body("More than one entity is exists, please contact admin");
		}
		boolean entityDeleted = false;
		try {
			entityDeleted = service.deleteAssetSubType(existingEntities.get(0), userDetails.getUsername());
		} catch (SitException e) {
			logger.error("Exception occured in Entity creation");
		}
		
		
		return ResponseEntity.ok(entityDeleted?"Entity deleted successfully":"Entity not deleted");
	}
	
	//====================Assets EndPoints=========================
}
