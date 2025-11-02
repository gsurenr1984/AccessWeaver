package com.sitha.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sitha.common.dao.SitAssetSubTypeDAO;
import com.sitha.common.dao.SitAssetTypeDAO;
import com.sitha.common.dto.EntityRequest;
import com.sitha.common.repository.AssetSubTypeRepository;
import com.sitha.common.repository.AssetTypeRepository;
import com.sitha.common.util.SitException;

@Service
public class AssetTypesMgmtService {
	private static final Logger logger = LoggerFactory.getLogger(AssetTypesMgmtService.class);
	private final AssetTypeRepository entityRepo;
	private final AssetSubTypeRepository assetSubTypeRepo;
	
	public AssetTypesMgmtService(AssetTypeRepository entityRepo, AssetSubTypeRepository assetSubTypeRepo) {
		this.entityRepo = entityRepo;
		this.assetSubTypeRepo = assetSubTypeRepo;
	}
	
	public List<SitAssetTypeDAO> getAssetTypeByName(String name) {
		return entityRepo.findByName(name);
		
	}
	
	public List<SitAssetTypeDAO> getActiveAssetTypes() {
		
		return getStatusAssetType(true);
	}
	
	public List<SitAssetTypeDAO> getAsstTypes() {
		List<SitAssetTypeDAO> entities = entityRepo.findAll();
		return entities;
	}
	
	public List<SitAssetTypeDAO> getStatusAssetType(boolean statusInd) {
		List<SitAssetTypeDAO> entities = entityRepo.findByActive(statusInd);
		if(entities == null) {
			entities = new ArrayList();
		}
		return entities;
	}
	
	public SitAssetTypeDAO createActiveAssetType(EntityRequest entity, String userName) throws SitException{
		SitAssetTypeDAO dao = new SitAssetTypeDAO();
		dao.setCreatedDate(new Date());
		dao.setCreatedBy(userName);
		dao.setDescription(entity.getDescription());
		dao.setName(entity.getName());
		dao.setActive(true);
		dao = entityRepo.save(dao);
		if(dao == null || dao.getId() == null) {
			logger.error("Entity is not saved into database");
			throw new SitException("Entity is not saved into database");
		}
		return dao;
	}
	
	public boolean deleteAssetType(SitAssetTypeDAO deleteAsset, String userName) throws SitException{
		if(deleteAsset == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		deleteAsset.setActive(false);
		deleteAsset.setUpdatedDate(new Date());
		deleteAsset.setUpdatedBy(userName);
		deleteAsset = entityRepo.save(deleteAsset);
		
		if(deleteAsset.getId() == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		return true;
	}
	
	//Subtype Code
	public List<SitAssetSubTypeDAO> getAssetSubTypeByName(String name) {
		return assetSubTypeRepo.findByName(name);
		
	}
	
	public List<SitAssetSubTypeDAO> getActiveAssetSubTypes() {
		
		return getStatusAssetSubType(true);
	}
	
	public List<SitAssetSubTypeDAO> getAssetSubTypes() {
		List<SitAssetSubTypeDAO> entities = assetSubTypeRepo.findAll();
		return entities;
	}
	
	public List<SitAssetSubTypeDAO> getStatusAssetSubType(boolean statusInd) {
		List<SitAssetSubTypeDAO> entities = assetSubTypeRepo.findByActive(statusInd);
		if(entities == null) {
			entities = new ArrayList();
		}
		return entities;
	}
	
	public SitAssetSubTypeDAO createActiveAssetSubType(EntityRequest entity, String userName) throws SitException{
		SitAssetSubTypeDAO dao = new SitAssetSubTypeDAO();
		dao.setCreatedBy(userName);
		dao.setCreatedDate(new Date());
		dao.setName(entity.getName());
		dao.setSitAssetTypeId(entity.getAssetTypeId());
		dao = assetSubTypeRepo.save(dao);
		if(dao == null || dao.getId() == null) {
			logger.error("Entity is not saved into database");
			throw new SitException("Entity is not saved into database");
		}
		return dao;
	}
	
	public boolean deleteAssetSubType(SitAssetSubTypeDAO deleteAsset, String userName) throws SitException{
		if(deleteAsset == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		deleteAsset.setActive(false);
		
		deleteAsset = assetSubTypeRepo.save(deleteAsset);
		
		if(deleteAsset.getId() == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		return true;
	}
}
