package com.undoschool.class_booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.undoschool.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Course extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(length = 1000)
	private String description;
}