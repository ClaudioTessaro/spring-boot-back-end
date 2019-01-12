package com.claudiotessaro.curso.services.exceptions;

public class DataIntegrityService extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public DataIntegrityService(String msg) {
		super(msg);
	}
	public DataIntegrityService(String msg, Throwable cause) {
		super(msg,cause);
	}
}
