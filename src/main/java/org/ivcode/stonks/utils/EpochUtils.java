package org.ivcode.stonks.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A utility for epoch in seconds
 * @author isaiah
 *
 */
public class EpochUtils {
	
	public static Long epoch(Date date) {
		long time = date.getTime();
		return TimeUnit.MILLISECONDS.toSeconds(time);
	}
	
	public static Date toDate(long epoch) {
		long time = TimeUnit.SECONDS.toMillis(epoch);
		return new Date(time);
	}
	
	public static Long epoch(LocalDate localDate) {
		return localDate.atStartOfDay().atZone(ZoneId.of("America/New_York")).toEpochSecond();
	}
	
	public static Long epoch(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.of("America/New_York")).toEpochSecond();
	}
}
