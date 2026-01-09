package com.springAi.Spring_Ai_Demo.Exception;

// this is custom exception class for the API
// ofcourse we need add in Service class

public class SpringAiCustomException extends RuntimeException {
	
	private final String errorCode;

	public SpringAiCustomException(String errorCode) {
		super();
		this.errorCode = "Resource Not found";
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	
	
	
	
	

}
