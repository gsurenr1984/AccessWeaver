package com.sitha.user.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sitha.common.dao.OTPValidationDAO;
import com.sitha.common.repository.OtpRepository;
import com.sitha.common.util.SitUtil;

@Service
public class OTPService {
	
	private final OtpRepository otpRepository;
	private final EmailService emailService;
	private static final Logger logger = LoggerFactory.getLogger(OTPService.class);
	
	public OTPService(OtpRepository otpRepository, EmailService emailService) {
		this.otpRepository = otpRepository;
		this.emailService = emailService;
	}
	
	public boolean generateOtp(String source) {
		OTPValidationDAO otp = new OTPValidationDAO();
		otp.setSource(source);
		String otpVal = randomOtpGenerated();
		otp.setOtp(otpVal);
		otp.setOtpSentTime(LocalDateTime.now());
		otpRepository.save(otp);
	//	emailService.sendOTPMail(source, otpVal);
		return false;
	}
	
	public boolean verifyOtp(String source, String userEnteredOtp) {
		logger.info("Verify OTP is call, Email/source={}, OTP={}", source, userEnteredOtp);
		OTPValidationDAO otp = otpRepository.findLatestOTPWhichNotVerified(source);
		
		logger.info("Database OTP, OTP={}", otp.getOtp());
		if(otp != null  && StringUtils.hasLength(otp.getOtp()) && userEnteredOtp.equalsIgnoreCase(otp.getOtp()) && SitUtil.isLessThanConfigMinutesAgo(otp.getOtpSentTime())) {
			otp.setVerified(true);
			otp.setOtpVerifiedTime(LocalDateTime.now());
			otp.setOtp(null);
			otpRepository.save(otp);
			return true;
		}
		return false;
	}
	
	private String randomOtpGenerated() {
		SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(1_000_000); // 0 to 999999
        return String.format("%06d", number);
	}
}
