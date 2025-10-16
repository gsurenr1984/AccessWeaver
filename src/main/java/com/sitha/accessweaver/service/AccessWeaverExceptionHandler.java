package com.sitha.accessweaver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AccessWeaverExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(AccessWeaverExceptionHandler.class);

	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleMethodNotSupported(Exception ex) {
		logger.error("Exception Occuered : ", ex);
        String message = "Request method '" + ex.getMessage() + "' is not supported for this endpoint.";
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
