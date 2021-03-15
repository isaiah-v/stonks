package org.ivcode.stonks.repository.ally.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.ivcode.stonks.repository.ally.model.ClockMarketStatus;

public class ClockMarketStatusAdapter extends XmlAdapter<String, ClockMarketStatus> {

	@Override
	public ClockMarketStatus unmarshal(String v) throws Exception {
		return ClockMarketStatus.getStatus(v);
	}

	@Override
	public String marshal(ClockMarketStatus v) throws Exception {
		return ClockMarketStatus.getCode(v);
	}

}
