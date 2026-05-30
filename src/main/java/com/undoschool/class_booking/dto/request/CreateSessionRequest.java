package com.undoschool.class_booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSessionRequest {

	@NotNull
	private String startTime;

	@NotNull
	private String endTime;

	@NotBlank
	private String timezone;
}