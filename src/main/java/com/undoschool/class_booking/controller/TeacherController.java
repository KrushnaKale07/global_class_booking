package com.undoschool.class_booking.controller;

import com.undoschool.class_booking.dto.request.CreateOfferingRequest;
import com.undoschool.class_booking.dto.request.CreateSessionRequest;
import com.undoschool.class_booking.dto.response.OfferingResponse;
import com.undoschool.class_booking.entity.Offering;
import com.undoschool.class_booking.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	@PostMapping("/offerings")
	public Offering createOffering(@RequestBody @Valid CreateOfferingRequest request) {
		return teacherService.createOffering(request);
	}

	@PostMapping("/offerings/{offeringId}/sessions")
	public String addSession(@PathVariable Long offeringId, @RequestBody @Valid CreateSessionRequest request) {

		teacherService.addSession(offeringId, request);

		return "Session Added";
	}

//	@GetMapping("/{teacherId}/offerings")
//	public List<Offering> getOfferings(@PathVariable Long teacherId) {
//		return teacherService.getTeacherOfferings(teacherId);
//	}

	@GetMapping("/{teacherId}/offerings")
	public List<OfferingResponse> getOfferings(@PathVariable Long teacherId) {
		return teacherService.getTeacherOfferings(teacherId);
	}
}