package com.undoschool.class_booking.entity;

import java.time.Instant;

import com.undoschool.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "bookings", uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "offering_id" }) })
public class Booking extends BaseEntity {

	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "offering_id")
	private Offering offering;

	@Column(nullable = false)
	private Instant bookedAt;

}