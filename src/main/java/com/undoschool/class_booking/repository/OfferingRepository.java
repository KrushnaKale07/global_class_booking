package com.undoschool.class_booking.repository;

import com.undoschool.class_booking.entity.Offering;
import com.undoschool.class_booking.entity.enums.OfferingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferingRepository extends JpaRepository<Offering, Long> {

	@Query("""
			SELECT o
			FROM Offering o
			JOIN FETCH o.course
			JOIN FETCH o.teacher
			WHERE o.teacher.id = :teacherId
			""")
	List<Offering> findOfferingsWithDetails(Long teacherId);

	List<Offering> findByStatus(OfferingStatus active);

//	List<Offering> findByTeacherId(Long teacherId);
//
//	List<Offering> findByStatus(OfferingStatus status);
}