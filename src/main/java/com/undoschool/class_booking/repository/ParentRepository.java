package com.undoschool.class_booking.repository;

import com.undoschool.class_booking.entity.Parent;

import jakarta.persistence.LockModeType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ParentRepository extends JpaRepository<Parent, Long> {

	boolean existsByEmail(String email);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Parent> findWithLockingById(Long id);
}