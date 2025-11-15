package com.sitha.common.dao;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="SIT_OTP_MANAGEMENT")
public class OTPValidationDAO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String otp;
    private LocalDateTime  otpSentTime;
    private String source;
    private boolean isVerified;
    private LocalDateTime  otpVerifiedTime;
}
