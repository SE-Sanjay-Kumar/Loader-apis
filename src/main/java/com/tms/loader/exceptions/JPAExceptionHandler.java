package com.tms.loader.exceptions;

@SuppressWarnings("serial")
public class JPAExceptionHandler extends RuntimeException {
	public JPAExceptionHandler() {
		super("Could not exceute due to jpa exception!!");
	}
}
