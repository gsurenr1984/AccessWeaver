package com.sitha.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitha.common.dao.SitAssetTypeDAO;

public interface AssetTypeRepository extends JpaRepository<SitAssetTypeDAO, Long> {
	List<SitAssetTypeDAO> findByActive(boolean active);
	Optional<SitAssetTypeDAO> findById(Long id);
	
	List<SitAssetTypeDAO> findByName(String name);

}
