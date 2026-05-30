package com.undoschool.class_booking.repository;

import com.undoschool.class_booking.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}