package com.sitha.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sitha.common.dao.SitAssetDAO;

public interface AssetRepository extends JpaRepository<SitAssetDAO, Long> {
	List<SitAssetDAO> findByStatus(String status);
	Optional<SitAssetDAO> findById(Long id);
	
	@Query("""
		    SELECT sa.status FROM SitAssetDAO sa
		    WHERE sa.status = :status
		    AND sa.parentAssetId IN (
		        SELECT sau.assetId FROM SitAssetUserDAO sau
		       JOIN SitAssetUserDAO sup on sup.id=sau.userProfileId
		        WHERE  sau.status = 'Active'
		        AND (sau.readAccess = true OR sau.writeAccess = true)
		    )
		""")
		List<SitAssetDAO> findAssetsByUserAndStatus(@Param("userName") String userName, @Param("status") String status);

	@Query("""
		    SELECT sa.status FROM SitAssetDAO sa
		    WHERE sa.status = :status
		    AND sa.parentAssetId IN (
		        SELECT sau.assetId FROM SitAssetUserDAO sau
		        JOIN SitAssetUserDAO sup on sup.id=sau.userProfileId
		        WHERE  sau.status = 'Active'
		        AND (sau.readAccess = true OR sau.writeAccess = true)
		    )
		""")List<SitAssetDAO> findAssetsByUser(@Param("userName") String userName);
	
	@Query("""
		    SELECT sa.status FROM SitAssetDAO sa
		    WHERE sa.status = :status
		    AND sa.parentAssetId IN (
		        SELECT sau.assetId FROM SitAssetUserDAO sau
		        JOIN SitAssetUserDAO sup on sup.id=sau.userProfileId
		        WHERE  sau.status = 'Active'
		        AND (sau.readAccess = true OR sau.writeAccess = true)
		    )
		""")List<SitAssetDAO> findAssetsByUserAndAssetType(@Param("userName") String userName, @Param("assetTypeId") Long assetTypeId);
	

}
