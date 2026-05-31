package com.undoschool.class_booking.service;

import com.undoschool.class_booking.dto.request.CreateOfferingRequest;
import com.undoschool.class_booking.dto.request.CreateSessionRequest;
import com.undoschool.class_booking.dto.response.OfferingResponse;
import com.undoschool.class_booking.entity.Offering;

import java.util.List;

public interface TeacherService {

	Offering createOffering(CreateOfferingRequest request);

	void addSession(Long offeringId, CreateSessionRequest request);

	List<OfferingResponse> getTeacherOfferings(Long teacherId);
}