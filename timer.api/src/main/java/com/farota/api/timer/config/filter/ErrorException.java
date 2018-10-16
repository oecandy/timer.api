package com.farota.api.timer.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farota.core.extend.ResponseCode;

public class ErrorException extends RuntimeException{
	
	private static final long serialVersionUID = 7753532345280907473L;
	
	private static Logger logger = LoggerFactory.getLogger(ErrorException.class);
	
	private ResponseCode responseCode;
	
	public ErrorException(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	
	public ErrorException(ResponseCode responseCode, String errorMessage) {
		logger.info(errorMessage);
		this.responseCode = responseCode;
	}
	

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

}