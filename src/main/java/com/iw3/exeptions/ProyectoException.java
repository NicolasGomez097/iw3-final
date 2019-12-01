package com.iw3.exeptions;

public class ProyectoException extends Exception {

	private static final long serialVersionUID = 154154519;

	public ProyectoException() {
	}

	public ProyectoException(String message) {
		super(message);
	}

	public ProyectoException(Throwable cause) {
		super(cause);
	}

	public ProyectoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProyectoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
