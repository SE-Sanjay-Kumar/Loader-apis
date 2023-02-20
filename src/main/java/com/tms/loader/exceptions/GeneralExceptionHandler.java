package com.tms.loader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.tms.loader.payloads.*;

@RestControllerAdvice
public class GeneralExceptionHandler {
	ApiResponse apiResp = new ApiResponse();
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException exception){
		apiResp.setMessage(exception.getMessage());
		apiResp.setSuccess(false);
		return new ResponseEntity<ApiResponse>(apiResp, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ConstraintViolationExceptionHandler.class)
	public ResponseEntity<ApiResponse> jpaSystemExceptionHandler(ConstraintViolationExceptionHandler exception){
		apiResp.setMessage(exception.getMessage());
		apiResp.setSuccess(false);
		return new ResponseEntity<ApiResponse>(apiResp, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ExceptionEnd.class)
	public ResponseEntity<ApiResponse> ExceptionHandler(ExceptionEnd exception){
		apiResp.setMessage(exception.getMessage());
		apiResp.setSuccess(false);
		return new ResponseEntity<ApiResponse>(apiResp, HttpStatus.NOT_ACCEPTABLE);
	}
}
