package org.ivcode.stonks.repository.ally.model.adapters;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ISOLocalTimeAdapter extends XmlAdapter<String, LocalTime> {

	@Override
	public LocalTime unmarshal(String v) throws Exception {
		return LocalTime.parse(v, DateTimeFormatter.ISO_TIME);
	}

	@Override
	public String marshal(LocalTime v) throws Exception {
		return v.format(DateTimeFormatter.ISO_TIME);
	}

}
