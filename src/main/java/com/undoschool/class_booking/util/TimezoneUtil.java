package com.undoschool.class_booking.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimezoneUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private TimezoneUtil() {
	}

	public static Instant convertToUtc(String localDateTime, String timezone) {

		LocalDateTime dateTime = LocalDateTime.parse(localDateTime, FORMATTER);

		ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of(timezone));

		return zonedDateTime.toInstant();
	}
}