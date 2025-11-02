package com.sitha.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitha.common.dao.OrganizationDAO;

public interface OrganizationRepository extends JpaRepository<OrganizationDAO, Long> {

}
