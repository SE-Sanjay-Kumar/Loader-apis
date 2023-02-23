package com.tms.loader.exceptions;

import com.tms.loader.utils.CurrentDateTime;

@SuppressWarnings("serial")
public class DataIntegrityExceptionHandler extends RuntimeException {
	public DataIntegrityExceptionHandler() {
		super("Violation of Data Integrity by current operation on "+CurrentDateTime.now);
	}
}
