package com.ing.training.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ing.training.domain.ErrorDetail;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleException(MethodArgumentNotValidException argInvalidException) {
	logger.error(argInvalidException.getMessage(), argInvalidException);
	BindingResult bindingResult = argInvalidException.getBindingResult();
	StringBuilder errorDetails = new StringBuilder();
	(bindingResult.getAllErrors()).stream().forEach(objectError -> errorDetails.append(objectError.toString()));
	ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.name(), errorDetails.toString());
	return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDetail> handleDataAccessException(DataAccessException dae) {
	logger.error(dae.getMessage(), dae);
	String errorMessage=messageSource.getMessage("error.method.persistence.error", new Object[]{}, LocaleContextHolder.getLocale());
	ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.name(), errorMessage);
	return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleException(Exception exception) {
	logger.error(exception.getMessage(), exception);
	ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
	return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
