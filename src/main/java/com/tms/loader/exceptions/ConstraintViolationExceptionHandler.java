package com.tms.loader.exceptions;


import com.tms.loader.utils.CurrentDateTime;

@SuppressWarnings("serial")
public class ConstraintViolationExceptionHandler extends RuntimeException {
	 
	public ConstraintViolationExceptionHandler(String attribute) {
		 super(String.format("Constraint violation by resource accessed on %s with attribute value %s",CurrentDateTime.now, attribute));
	}

}
