package com.sitha.accessweaver.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitha.accessweaver.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

}
