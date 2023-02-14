package com.tms.loader.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class ExceptionEnd extends RuntimeException {
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	static LocalDateTime now = LocalDateTime.now(); 
	public ExceptionEnd() {
		 super(String.format("Exception occured during resource accessed on %s ",dtf.format(now)));
		
	}
}
