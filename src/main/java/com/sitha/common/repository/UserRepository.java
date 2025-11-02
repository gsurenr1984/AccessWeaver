package com.sitha.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitha.accessweaver.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String userName);
}
