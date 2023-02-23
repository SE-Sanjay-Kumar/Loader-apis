package com.tms.loader.exceptions;


import com.tms.loader.utils.CurrentDateTime;

@SuppressWarnings("serial")
public class AllExceptionHandler extends RuntimeException {
	public AllExceptionHandler() {
		 super(String.format("Exception occured during resource accessed on %s ",CurrentDateTime.now));
		
	}
}
