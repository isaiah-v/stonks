package org.ivcode.stonks.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {

	public static final String ZONE = "America/New_York";
	public static final ZoneId MARKET_ZONE_ID = ZoneId.of(ZONE);
	
	public static String format(LocalDate date, DateTimeFormatter formatter) {
		return date==null ? null : date.format(formatter);
	}
	
	public static String format(LocalTime date, DateTimeFormatter formatter) {
		return date==null ? null : date.format(formatter);
	}
	
	public static LocalDate toLocalDate(Date date) {
		return toZonedDateTime(date).toLocalDate();
	}
	
	public static LocalTime toLocalTime(Date date) {
		return toZonedDateTime(date).toLocalTime();
	}
	
	public static LocalDateTime toLocalDateTime(Date date) {
		return toZonedDateTime(date).toLocalDateTime();
	}
	
	public static ZonedDateTime toZonedDateTime(Date date) {
		return date.toInstant().atZone(MARKET_ZONE_ID);
	}
	
	public static ZonedDateTime now() {
		return ZonedDateTime.now(MARKET_ZONE_ID);
	}
	
	public static long toMilli(ZonedDateTime time) {
		return time.toInstant().toEpochMilli();
	}
	
	public static Date toDate(LocalDateTime dateTime) {
		long date = dateTime.atZone(MARKET_ZONE_ID).toInstant().toEpochMilli();
		return new Date(date);
	}
	
	public static Date toDate(LocalDate date) {
		long d = date.atStartOfDay(MARKET_ZONE_ID).toInstant().toEpochMilli();
		return new Date(d);
	}
}
