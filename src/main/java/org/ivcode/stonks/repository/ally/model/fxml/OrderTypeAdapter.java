package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OrderTypeAdapter extends XmlAdapter<String, OrderType> {

	@Override
	public OrderType unmarshal(String v) throws Exception {
		return v==null || v.isEmpty() ? null : OrderType.find(v.charAt(0));
	}

	@Override
	public String marshal(OrderType v) throws Exception {
		return v==null ? null : OrderType.getId(v).toString();
	}

}
