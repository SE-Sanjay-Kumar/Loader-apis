package com.tms.loader.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class CJpaSystemException extends RuntimeException {
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	static LocalDateTime now = LocalDateTime.now();  

	public CJpaSystemException(String attribute) {
		 super(String.format("Constraint violation by resource accessed on %s with attribute %s",dtf.format(now), attribute));
		
	}

}
