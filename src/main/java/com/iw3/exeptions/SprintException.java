package com.iw3.exeptions;

public class SprintException extends Exception {

	private static final long serialVersionUID = 154154518;

	public SprintException() {
	}

	public SprintException(String message) {
		super(message);
	}

	public SprintException(Throwable cause) {
		super(cause);
	}

	public SprintException(String message, Throwable cause) {
		super(message, cause);
	}

	public SprintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
