package com.iw3.exeptions;

public class ListaException extends Exception {

	private static final long serialVersionUID = 154154516;

	public ListaException() {
	}

	public ListaException(String message) {
		super(message);
	}

	public ListaException(Throwable cause) {
		super(cause);
	}

	public ListaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ListaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
