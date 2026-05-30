package com.undoschool.class_booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OfferingResponse {

	private Long offeringId;

	private String offeringName;

	private String courseTitle;

	private String teacherName;

	private List<SessionResponse> sessions;
}