package org.ivcode.stonks.repository.ally.model.adapters;

import static org.apache.commons.lang3.BooleanUtils.*;
import static org.apache.commons.lang3.math.NumberUtils.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public Boolean unmarshal(String v) throws Exception {
		if(v==null || v.isBlank()) {
			return null;
		}
		
		if(isCreatable(v)) {
			return toInt(v)>0;
		} else {
			return toBooleanObject(v);
		}
	}

	@Override
	public String marshal(Boolean v) throws Exception {
		return v.toString();
	}

}
