package com.undoschool.class_booking.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.undoschool.class_booking.entity.enums.OfferingStatus;
import com.undoschool.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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

	@OneToMany(mappedBy = "offering", fetch = FetchType.LAZY)
	private List<Session> sessions;
}