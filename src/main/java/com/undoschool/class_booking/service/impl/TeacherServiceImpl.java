package com.undoschool.class_booking.service.impl;

import com.undoschool.class_booking.dto.request.CreateOfferingRequest;
import com.undoschool.class_booking.dto.request.CreateSessionRequest;
import com.undoschool.class_booking.entity.*;
import com.undoschool.class_booking.entity.enums.OfferingStatus;
import com.undoschool.class_booking.exception.ResourceNotFoundException;
import com.undoschool.class_booking.repository.*;
import com.undoschool.class_booking.service.TeacherService;
import com.undoschool.class_booking.util.TimezoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;
	private final OfferingRepository offeringRepository;
	private final SessionRepository sessionRepository;

	@Override
	public Offering createOffering(CreateOfferingRequest request) {

		Teacher teacher = teacherRepository.findById(request.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		Offering offering = new Offering();

		offering.setName(request.getName());
		offering.setTeacher(teacher);
		offering.setCourse(course);
		offering.setStatus(OfferingStatus.ACTIVE);

		return offeringRepository.save(offering);
	}

	@Override
	public void addSession(Long offeringId, CreateSessionRequest request) {

		Offering offering = offeringRepository.findById(offeringId)
				.orElseThrow(() -> new ResourceNotFoundException("Offering not found"));

		Instant startUtc = TimezoneUtil.convertToUtc(request.getStartTime(), request.getTimezone());

		Instant endUtc = TimezoneUtil.convertToUtc(request.getEndTime(), request.getTimezone());

		Session session = new Session();

		session.setOffering(offering);
		session.setStartTime(startUtc);
		session.setEndTime(endUtc);

		sessionRepository.save(session);
	}

	@Override
	public List<Offering> getTeacherOfferings(Long teacherId) {
		return offeringRepository.findByTeacherId(teacherId);
	}
}