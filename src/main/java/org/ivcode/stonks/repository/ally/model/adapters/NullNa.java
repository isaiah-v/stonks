package org.ivcode.stonks.repository.ally.model.adapters;



import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NullNa extends XmlAdapter<String, String>{

	private static final String NA = "na";
	
	@Override
	public String unmarshal(String v) throws Exception {
		return null;
	}

	@Override
	public String marshal(String v) throws Exception {
		return v==null ? NA : v;
	}

}
