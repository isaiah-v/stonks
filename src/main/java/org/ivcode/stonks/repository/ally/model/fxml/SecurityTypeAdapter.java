package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SecurityTypeAdapter extends XmlAdapter<String, SecurityType> {

	@Override
	public SecurityType unmarshal(String v) throws Exception {
		return SecurityType.find(v);
	}

	@Override
	public String marshal(SecurityType v) throws Exception {
		return SecurityType.getId(v);
	}
}
