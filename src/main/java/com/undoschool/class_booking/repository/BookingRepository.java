package com.undoschool.class_booking.repository;

import com.undoschool.class_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	boolean existsByParentIdAndOfferingId(Long parentId, Long offeringId);

	List<Booking> findByParentId(Long parentId);
	

}