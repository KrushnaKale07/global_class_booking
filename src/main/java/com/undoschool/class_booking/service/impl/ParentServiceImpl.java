package com.undoschool.class_booking.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.undoschool.class_booking.dto.request.BookingRequest;
import com.undoschool.class_booking.dto.response.BookingResponse;
import com.undoschool.class_booking.dto.response.OfferingResponse;
import com.undoschool.class_booking.dto.response.SessionResponse;
import com.undoschool.class_booking.entity.Booking;
import com.undoschool.class_booking.entity.Offering;
import com.undoschool.class_booking.entity.Parent;
import com.undoschool.class_booking.entity.Session;
import com.undoschool.class_booking.entity.enums.OfferingStatus;
import com.undoschool.class_booking.exception.BookingConflictException;
import com.undoschool.class_booking.exception.DuplicateBookingException;
import com.undoschool.class_booking.exception.ResourceNotFoundException;
import com.undoschool.class_booking.repository.BookingRepository;
import com.undoschool.class_booking.repository.OfferingRepository;
import com.undoschool.class_booking.repository.ParentRepository;
import com.undoschool.class_booking.repository.SessionRepository;
import com.undoschool.class_booking.service.ParentService;
import com.undoschool.class_booking.util.TimezoneUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

	private final OfferingRepository offeringRepository;
	private final BookingRepository bookingRepository;
	private final ParentRepository parentRepository;
	private final SessionRepository sessionRepository;

	@Override
	public List<OfferingResponse> getAvailableOfferings(String timezone) {

		List<Offering> offerings = offeringRepository.findByStatus(OfferingStatus.ACTIVE);

		return offerings.stream().map(offering -> mapOffering(offering, timezone)).toList();
	}

	@Transactional
	@Override
	public BookingResponse bookOffering(BookingRequest request) {

		Parent parent = parentRepository.findById(request.getParentId())
				.orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

		Offering offering = offeringRepository.findById(request.getOfferingId())
				.orElseThrow(() -> new ResourceNotFoundException("Offering not found"));

		validateDuplicateBooking(parent.getId(), offering.getId());

		validateBookingConflict(parent.getId(), offering.getId());

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

	private void validateBookingConflict(Long parentId, Long offeringId) {

		List<Booking> existingBookings = bookingRepository.findByParentId(parentId);

		SessionRepository sessionRepository = null;
		List<Session> newOfferingSessions = sessionRepository.findByOfferingId(offeringId);

		for (Booking booking : existingBookings) {

			List<Session> bookedSessions = sessionRepository.findByOfferingId(booking.getOffering().getId());

			for (Session bookedSession : bookedSessions) {

				for (Session newSession : newOfferingSessions) {

					boolean overlap = bookedSession.getStartTime().isBefore(newSession.getEndTime())
							&& bookedSession.getEndTime().isAfter(newSession.getStartTime());

					if (overlap) {

						throw new BookingConflictException("Booking conflicts with an existing session");
					}
				}
			}
		}
	}

	private void validateDuplicateBooking(Long parentId, Long offeringId) {

		boolean alreadyBooked = bookingRepository.existsByParentIdAndOfferingId(parentId, offeringId);

		if (alreadyBooked) {

			throw new DuplicateBookingException("Parent has already booked this offering");
		}
	}
}
