package org.ivcode.stonks.repository.ally.model.fxml;

public enum SecurityType {
	COMMON_STOCK("CS");

	private final String id;

	private SecurityType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public static String getId(SecurityType value) {
		return value==null ? null : value.id;
	}
	
	public static SecurityType find(String id) {
		if(id==null) {
			return null;
		}
		id = id.trim();
		
		for(SecurityType st : SecurityType.values()) {
			if(id.equalsIgnoreCase(st.id)) {
				return st;
			}
		}
		
		return null;
	}
}
