package org.ivcode.stonks.repository.ally.model.adapters;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSXXX");

	@Override
	public ZonedDateTime unmarshal(String v) throws Exception {
		ZonedDateTime zonedDateTime =ZonedDateTime.parse(v, FORMATTER);
		return zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
	}

	@Override
	public String marshal(ZonedDateTime v) throws Exception {
		return v.format(FORMATTER);
	}
}
