package com.undoschool.class_booking.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.undoschool.class_booking.dto.request.BookingRequest;
import com.undoschool.class_booking.dto.response.BookingResponse;
import com.undoschool.class_booking.dto.response.OfferingResponse;
import com.undoschool.class_booking.dto.response.SessionResponse;
import com.undoschool.class_booking.entity.Booking;
import com.undoschool.class_booking.entity.Offering;
import com.undoschool.class_booking.entity.Parent;
import com.undoschool.class_booking.entity.enums.OfferingStatus;
import com.undoschool.class_booking.exception.ResourceNotFoundException;
import com.undoschool.class_booking.repository.BookingRepository;
import com.undoschool.class_booking.repository.OfferingRepository;
import com.undoschool.class_booking.repository.ParentRepository;
import com.undoschool.class_booking.service.ParentService;
import com.undoschool.class_booking.util.TimezoneUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

	private final OfferingRepository offeringRepository;
	private final BookingRepository bookingRepository;
	private final ParentRepository parentRepository;

	@Override
	public List<OfferingResponse> getAvailableOfferings(String timezone) {

		List<Offering> offerings = offeringRepository.findByStatus(OfferingStatus.ACTIVE);

		return offerings.stream().map(offering -> mapOffering(offering, timezone)).toList();
	}

	@Override
	public BookingResponse bookOffering(BookingRequest request) {

		Parent parent = parentRepository.findById(request.getParentId())
				.orElseThrow(() -> new ResourceNotFoundException("Parent not found with id: " + request.getParentId()));

		Offering offering = offeringRepository.findById(request.getOfferingId()).orElseThrow(
				() -> new ResourceNotFoundException("Offering not found with id: " + request.getOfferingId()));
		;

		Booking booking = new Booking();

		booking.setParent(parent);
		booking.setOffering(offering);
		booking.setBookedAt(Instant.now());

		bookingRepository.save(booking);

		return BookingResponse.builder().bookingId(booking.getId()).offeringId(offering.getId())
				.offeringName(offering.getName()).build();
	}

	@Override
	public List<BookingResponse> getBookings(Long parentId) {

		return bookingRepository.findByParentId(parentId).stream()
				.map(booking -> BookingResponse.builder().bookingId(booking.getId())
						.offeringId(booking.getOffering().getId()).offeringName(booking.getOffering().getName())
						.build())
				.toList();
	}

	private OfferingResponse mapOffering(Offering offering, String timezone) {

		List<SessionResponse> sessions = offering.getSessions().stream()
				.map(session -> SessionResponse.builder().sessionId(session.getId())
						.startTime(TimezoneUtil.convertFromUtc(session.getStartTime(), timezone))
						.endTime(TimezoneUtil.convertFromUtc(session.getEndTime(), timezone)).build())
				.toList();

		return OfferingResponse.builder().offeringId(offering.getId()).offeringName(offering.getName())
				.courseTitle(offering.getCourse().getTitle()).teacherName(offering.getTeacher().getName())
				.sessions(sessions).build();
	}

}
