package com.app.exceptions;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
		super();
	}

	public ProductNotFoundException(String errormessage, Throwable throwable) {
		super(errormessage, throwable);
	}

	public ProductNotFoundException(String errormessage) {
		super(errormessage);
	}

}
