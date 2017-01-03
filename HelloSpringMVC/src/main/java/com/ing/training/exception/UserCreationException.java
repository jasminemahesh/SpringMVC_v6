package com.ing.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="User Creation Failed Due to Some Conflict")
public class UserCreationException extends RuntimeException {
	
	public UserCreationException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
