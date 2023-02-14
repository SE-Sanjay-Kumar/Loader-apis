package com.tms.loader.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
	}
}
