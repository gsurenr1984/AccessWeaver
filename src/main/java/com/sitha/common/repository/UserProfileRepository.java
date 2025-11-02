package com.sitha.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sitha.common.dao.UserDetail;

public interface UserProfileRepository extends JpaRepository<UserDetail, Long> {
	@Query(value="Select * from SIT_USER_PROFILE where (:userName IS NULL or username=:userName) and (:email IS NULL or email= :email) and (:phoneNumber IS NULL or phone_number=:phoneNumber)", nativeQuery= true)
	Optional<UserDetail> findByUniqueUser(String userName, String email, String phoneNumber);
	Optional<UserDetail> findByUsername(String username);
	Optional<UserDetail> findByPhoneNumber(String phoneNumber);
	Optional<UserDetail> findByEmail(String email);
}
