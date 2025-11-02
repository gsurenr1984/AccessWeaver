package com.sitha.user.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sitha.common.dao.SitAssetDAO;
import com.sitha.common.dto.AssetRequest;
import com.sitha.common.repository.AssetRepository;
import com.sitha.common.repository.UserRepository;
import com.sitha.common.util.SitException;

@Service
public class AssetMgmtService {
	private static final Logger logger = LoggerFactory.getLogger(AssetMgmtService.class);
	private final AssetRepository assetRepo;
	
	public AssetMgmtService(AssetRepository assetRepo) {
		this.assetRepo = assetRepo;
	}
	
	public List<SitAssetDAO> getAssetsByUserNameAndStatus(String userName, String status) {
		if(!StringUtils.hasLength(userName) || !StringUtils.hasLength(status)) {
			logger.info("Invalid Username or status");
			return null;
		}
		
		return assetRepo.findAssetsByUserAndStatus(userName, status);
		
	}
	
	public List<SitAssetDAO> getAssetsByUserName(String userName) {
		if(!StringUtils.hasLength(userName)) {
			logger.info("Invalid Username");
			return null;
		}
		
		return assetRepo.findAssetsByUser(userName);
		
	}
	
	public SitAssetDAO createActiveAsset(AssetRequest assetRequest, String userName) throws SitException{
		List<SitAssetDAO> optAssetDao = assetRepo.findAssetsByUserAndAssetType(userName, assetRequest.getAssetTypeId());
		
		SitAssetDAO dao = new SitAssetDAO();
		if(optAssetDao != null && optAssetDao.stream().filter(x->x.getAssetNumber().equalsIgnoreCase(assetRequest.getAssetNumber())).count()>0) {
			logger.info("This Asset is already exists");
			return null;
		} 
		
		dao.setParentAssetId(assetRequest.getParentAssetId());
		dao.setCreatedDate(new Date());
		dao.setCreatedBy(userName);
		dao.setAssetAddress(assetRequest.getAssetAddress());
		dao.setAssetDescription(assetRequest.getAssetDescription());
		dao.setAssetName(assetRequest.getAssetName());
		dao.setAssetNumber(assetRequest.getAssetNumber());
		dao.setAssetProperties(assetRequest.getAssetProperties());
		dao.setAssetSubType(assetRequest.getAssetSubType());
		dao.setAssetTypeId(assetRequest.getAssetTypeId());
	
		dao.setCreatedDate(new Date());
		
		
		dao = assetRepo.save(dao);
		if(dao == null || dao.getId() == null) {
			logger.error("Entity is not saved into database");
			throw new SitException("Entity is not saved into database");
		}
		return dao;
	}
	
	public boolean deleteAssetType(SitAssetDAO deleteAsset, String userName) throws SitException{
		if(deleteAsset == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		deleteAsset.setStatus("Deleted");
		deleteAsset.setUpdatedDate(new Date());
		deleteAsset.setUpdatedBy(userName);
		deleteAsset = assetRepo.save(deleteAsset);
		
		if(deleteAsset.getId() == null) {
			logger.error("Entity is not present");
			throw new SitException("Entity is not present");
		}
		return true;
	}
	
	//Subtype Code
	
}
