package com.undoschool.class_booking.repository;

import com.undoschool.class_booking.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferingRepository extends JpaRepository<Offering, Long> {

	List<Offering> findByTeacherId(Long teacherId);
}