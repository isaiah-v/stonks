package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MiscFeeTypeAdapter extends XmlAdapter<Integer, MiscFeeType> {

	@Override
	public MiscFeeType unmarshal(Integer v) throws Exception {
		return MiscFeeType.find(v);
	}

	@Override
	public Integer marshal(MiscFeeType v) throws Exception {
		return MiscFeeType.getId(v);
	}

}
