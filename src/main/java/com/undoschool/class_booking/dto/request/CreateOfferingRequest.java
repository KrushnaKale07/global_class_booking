package com.undoschool.class_booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOfferingRequest {

	@NotNull
	private Long courseId;

	@NotNull
	private Long teacherId;

	@NotBlank
	private String name;
}