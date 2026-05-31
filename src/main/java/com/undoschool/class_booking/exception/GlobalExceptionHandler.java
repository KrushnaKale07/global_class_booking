package com.undoschool.class_booking.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateBookingException.class)
	public ResponseEntity<?> handleDuplicateBooking(DuplicateBookingException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("timestamp", Instant.now(), "message", ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("timestamp", Instant.now(), "message", "Duplicate booking detected"));
	}
}