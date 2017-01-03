package com.ing.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Bad Request")
public class BadRequestException extends RuntimeException {
	
	public BadRequestException(String msg)
	{
		super(msg);
	}

}
