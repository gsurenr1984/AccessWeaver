package com.sitha.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitha.common.dao.SitAssetSubTypeDAO;

public interface AssetSubTypeRepository extends JpaRepository<SitAssetSubTypeDAO, Long> {
	List<SitAssetSubTypeDAO> findByActive(boolean active);
	Optional<SitAssetSubTypeDAO> findById(Long id);
	
	List<SitAssetSubTypeDAO> findByName(String name);

}
