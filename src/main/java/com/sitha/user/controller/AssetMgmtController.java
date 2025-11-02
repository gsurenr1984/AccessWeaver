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

import com.sitha.common.dao.SitAssetDAO;
import com.sitha.common.dto.AssetRequest;
import com.sitha.common.util.SitException;
import com.sitha.user.service.AssetMgmtService;

@RestController
@RequestMapping("/api/asset")
public class AssetMgmtController {
	
	private static final Logger logger = LoggerFactory.getLogger(AssetMgmtController.class);
	private final AssetMgmtService service;
	
	public AssetMgmtController(AssetMgmtService service){
		this.service = service;
	}
	
	@GetMapping({"/{entityStatusInd}", "/"})
	public ResponseEntity<?> getAssets(@PathVariable(required = false) String entityStatusInd,
			@AuthenticationPrincipal UserDetails userDetails) {
		List<SitAssetDAO> entities = new ArrayList<>();
		if(StringUtils.hasLength(entityStatusInd)) {
				entities.addAll(service.getAssetsByUserNameAndStatus(userDetails.getUsername(), entityStatusInd));
		} else {
			entities.addAll(service.getAssetsByUserName(userDetails.getUsername()));
		}
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createSubAsset(@RequestBody AssetRequest entity,
			@AuthenticationPrincipal UserDetails userDetails) {
		
		
		SitAssetDAO savedRecord = null;
		try {
			savedRecord = service.createActiveAsset(entity, userDetails.getUsername());
		} catch (SitException e) {
			logger.error("Exception occured in Entity creation");
		}
		
		return ResponseEntity.ok(savedRecord);
	}
}
