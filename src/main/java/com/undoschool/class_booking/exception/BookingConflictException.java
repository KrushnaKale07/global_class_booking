package com.undoschool.class_booking.exception;

public class BookingConflictException extends RuntimeException {

	private static final long serialVersionUID = 7632426668481801564L;

	public BookingConflictException(String message) {
		super(message);
	}
}