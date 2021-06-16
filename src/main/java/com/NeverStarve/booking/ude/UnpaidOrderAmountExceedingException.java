package com.NeverStarve.booking.ude;

public class UnpaidOrderAmountExceedingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnpaidOrderAmountExceedingException() {
		super();
	}

	public UnpaidOrderAmountExceedingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnpaidOrderAmountExceedingException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnpaidOrderAmountExceedingException(String message) {
		super(message);
	}

	public UnpaidOrderAmountExceedingException(Throwable cause) {
		super(cause);
	}
	
}
