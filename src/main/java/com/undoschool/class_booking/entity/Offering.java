package com.undoschool.class_booking.entity;

import com.undoschool.class_booking.entity.enums.OfferingStatus;
import com.undoschool.entity.base.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offerings")
@Getter
@Setter
public class Offering extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private OfferingStatus status;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
}