package com.springAi.Spring_Ai_Demo.Exception;

import java.time.LocalDateTime;

public class ApiError {
	
	private int errorCode;
	private String message;
	private int status;
	private LocalDateTime timeStamp = LocalDateTime.now();

}
