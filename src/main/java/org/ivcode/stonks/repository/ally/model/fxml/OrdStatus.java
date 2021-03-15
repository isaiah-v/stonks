package org.ivcode.stonks.repository.ally.model.fxml;

import static org.apache.commons.lang3.StringUtils.*;

public enum OrdStatus {
	NEW ("0"),
	PARTIALLY_FILLED ("1"),
	FILLED ("2"),
	DONE_FOR_DAY ("3"),
	CANCELED ("4"),
	PENDING_CANCEL ("6"),
	STOPPED ("7"),
	REJECTED ("8"),
	SUSPENDED ("9"),
	PENDING_NEW ("A"),
	CALCULATED ("B"),
	EXPIRED ("C"),
	ACCEPTED_FOR_BIDDING ("D"),
	PENDING_REPLACE ("E"),
	;
	
	private final String id;
	private OrdStatus(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public static OrdStatus find(String id) {
		id = trimToNull(upperCase(id));
		if(id==null) {
			return null;
		}
		
		for(OrdStatus ordStatus : OrdStatus.values()) {
			if(id.equals(ordStatus.id)) {
				return ordStatus;
			}
		}
		
		return null;
	}
	
	public static String getId(OrdStatus ordStatus) {
		return ordStatus==null ? null : ordStatus.id;
	}
}
