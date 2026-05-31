package com.undoschool.class_booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import com.undoschool.entity.base.BaseEntity;

@Entity
@Table(name = "bookings", uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "offering_id" }) })
public class Booking extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "offering_id")
	private Offering offering;

	@Column(nullable = false)
	private Instant bookedAt;
}