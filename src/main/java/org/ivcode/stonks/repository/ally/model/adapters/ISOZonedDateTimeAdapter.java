package org.ivcode.stonks.repository.ally.model.adapters;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ISOZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

	@Override
	public ZonedDateTime unmarshal(String v) throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);
		return zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
	}
	
	@Override
	public String marshal(ZonedDateTime v) throws Exception {
		return v.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
	}
}
