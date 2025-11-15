package com.sitha.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sitha.common.dao.OTPValidationDAO;

public interface OtpRepository extends JpaRepository<OTPValidationDAO, Long> {
	
	@Query(value="Select * from SIT_OTP_MANAGEMENT where source=:source and is_verified= false order by otp_sent_time desc LIMIT 1", nativeQuery= true)
	OTPValidationDAO findLatestOTPWhichNotVerified(String source);
}
