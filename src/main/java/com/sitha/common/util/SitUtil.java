package com.sitha.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class SitUtil {
	private static final String EMAIL_REGEX =
	        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	
	private static final int otpValidationTimeInMin =10; 

	    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	    public static boolean isValidEmail(String email) {
	        return email != null && EMAIL_PATTERN.matcher(email).matches();
	    }
	    
	    
	    public static boolean isLessThanConfigMinutesAgo(LocalDateTime inputTime) {
	        LocalDateTime now = LocalDateTime.now();
	        Duration duration = Duration.between(inputTime, now);
	        return !inputTime.isAfter(now) && duration.toMinutes() < otpValidationTimeInMin;
	    }

}
