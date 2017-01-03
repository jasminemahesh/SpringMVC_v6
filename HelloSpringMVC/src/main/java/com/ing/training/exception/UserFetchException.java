package com.ing.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User Not Found")
public class UserFetchException extends RuntimeException {
	
	public UserFetchException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
