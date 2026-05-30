package com.undoschool.class_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.undoschool.class_booking.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	boolean existsByEmail(String email);
}
