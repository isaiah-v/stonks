package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OrdStatusAdapter extends XmlAdapter<String, OrdStatus> {

	@Override
	public OrdStatus unmarshal(String v) throws Exception {
		return OrdStatus.find(v);
	}

	@Override
	public String marshal(OrdStatus v) throws Exception {
		return OrdStatus.getId(v);
	}

}
