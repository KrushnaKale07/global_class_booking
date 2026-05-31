package com.undoschool.class_booking.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferingResponse {

//	private Long offeringId;
//
//	private String offeringName;
//
//	private String courseTitle;
//
//	private String teacherName;
//
	private List<SessionResponse> sessions;

	private Long id;

	private String offeringName;

	private String courseName;

	private String teacherName;
}