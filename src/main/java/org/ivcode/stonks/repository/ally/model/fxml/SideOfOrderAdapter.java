package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SideOfOrderAdapter extends XmlAdapter<String, SideOfOrder> {

	@Override
	public SideOfOrder unmarshal(String v) throws Exception {
		return SideOfOrder.find(v.charAt(0));
	}

	@Override
	public String marshal(SideOfOrder v) throws Exception {
		Character ch = SideOfOrder.getId(v);
		return ch == null ? null : ch.toString();
	}

}
