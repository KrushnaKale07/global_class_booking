package com.undoschool.class_booking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SessionRequest {

	@NotBlank
	@Schema(example = "2026-06-07T18:00:00", description = "Teacher local start time")
	private String startTime;

	@NotBlank
	@Schema(example = "2026-06-07T19:00:00", description = "Teacher local end time")
	private String endTime;

	@NotBlank
	@Schema(example = "Asia/Kolkata", description = "Teacher timezone")
	private String timezone;
}