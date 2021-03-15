package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeInForceAdapter extends XmlAdapter<Integer, TimeInForce> {

	@Override
	public TimeInForce unmarshal(Integer v) throws Exception {
		return TimeInForce.find(v);
	}

	@Override
	public Integer marshal(TimeInForce v) throws Exception {
		return TimeInForce.getId(v);
	}

}
