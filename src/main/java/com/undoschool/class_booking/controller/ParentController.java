package com.undoschool.class_booking.controller;

import com.undoschool.class_booking.dto.request.BookingRequest;
import com.undoschool.class_booking.dto.response.BookingResponse;
import com.undoschool.class_booking.dto.response.OfferingResponse;
import com.undoschool.class_booking.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

	private final ParentService parentService;

	@GetMapping("/offerings")
	public List<OfferingResponse> getOfferings(@RequestParam String timezone) {
		return parentService.getAvailableOfferings(timezone);
	}

	@PostMapping("/bookings")
	public BookingResponse bookOffering(@RequestBody BookingRequest request) {
		return parentService.bookOffering(request);
	}

	@GetMapping("/{parentId}/bookings")
	public List<BookingResponse> getBookings(@PathVariable Long parentId) {
		return parentService.getBookings(parentId);
	}
}