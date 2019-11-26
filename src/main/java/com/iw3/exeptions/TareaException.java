package com.iw3.exeptions;

public class TareaException extends Exception {

	private static final long serialVersionUID = 154154517;

	public TareaException() {
	}

	public TareaException(String message) {
		super(message);
	}

	public TareaException(Throwable cause) {
		super(cause);
	}

	public TareaException(String message, Throwable cause) {
		super(message, cause);
	}

	public TareaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
