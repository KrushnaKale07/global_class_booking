package com.undoschool.class_booking.util;

import java.time.*;

public class TimezoneUtil {

	private TimezoneUtil() {
	}

	public static Instant convertToUtc(String localDateTime, String timezone) {

		LocalDateTime dateTime = LocalDateTime.parse(localDateTime);

		ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of(timezone));

		return zonedDateTime.toInstant();
	}

	public static String convertFromUtc(Instant utcTime, String timezone) {

		return utcTime.atZone(ZoneId.of(timezone)).toLocalDateTime().toString();
	}
}