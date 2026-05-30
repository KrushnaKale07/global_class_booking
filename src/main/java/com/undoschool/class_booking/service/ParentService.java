package com.undoschool.class_booking.service;

import com.undoschool.class_booking.dto.request.BookingRequest;
import com.undoschool.class_booking.dto.response.BookingResponse;
import com.undoschool.class_booking.dto.response.OfferingResponse;

import java.util.List;

public interface ParentService {

	List<OfferingResponse> getAvailableOfferings(String timezone);

	BookingResponse bookOffering(BookingRequest request);

	List<BookingResponse> getBookings(Long parentId);
}