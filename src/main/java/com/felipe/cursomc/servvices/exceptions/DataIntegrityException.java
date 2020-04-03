package com.felipe.cursomc.servvices.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = -5013750193700138633L;

	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
