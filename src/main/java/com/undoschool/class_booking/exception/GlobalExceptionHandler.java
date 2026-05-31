package com.undoschool.class_booking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.undoschool.class_booking.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateBookingException.class)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleDuplicateBooking(DuplicateBookingException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.builder().success(false).message(ex.getMessage()).data(null).build());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.builder().success(false).message(ex.getMessage()).data(null).build());
	}
}