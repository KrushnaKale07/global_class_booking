package com.undoschool.class_booking.exception;

public class DuplicateBookingException extends RuntimeException {

	private static final long serialVersionUID = -4984896947423943445L;

	public DuplicateBookingException(String message) {
		super(message);
	}
}