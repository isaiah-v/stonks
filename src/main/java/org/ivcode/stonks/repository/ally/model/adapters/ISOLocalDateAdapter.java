package org.ivcode.stonks.repository.ally.model.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ISOLocalDateAdapter extends XmlAdapter<String, LocalDate> {
	
	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.format(DateTimeFormatter.ISO_DATE);
	}

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v, DateTimeFormatter.ISO_DATE);
	}

}