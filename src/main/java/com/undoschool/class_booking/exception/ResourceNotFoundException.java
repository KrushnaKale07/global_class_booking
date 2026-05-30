package com.undoschool.class_booking.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7459576794534886909L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}