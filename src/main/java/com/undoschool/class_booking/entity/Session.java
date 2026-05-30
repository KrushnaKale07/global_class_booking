package com.undoschool.class_booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import com.undoschool.entity.base.BaseEntity;

@Entity
@Table(name = "sessions")
@Getter
@Setter
public class Session extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "offering_id")
	private Offering offering;

	@Column(nullable = false)
	private Instant startTime;

	@Column(nullable = false)
	private Instant endTime;
}