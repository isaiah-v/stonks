package org.ivcode.stonks.repository.ally.model.fxml;

public enum OrderType {
	MARKET ('1'),
	LIMIT ('2'),
	STOP ('3'),
	STOP_LIMIT ('4');
	
	private final char id;
	private OrderType(char id) {
		this.id = id;
	}
	
	public static OrderType find(Character id) {
		if(id==null) {
			return null;
		}
		
		for(OrderType type : OrderType.values()) {
			if(id.equals(type.id)) {
				return type;
			}
		}
		
		return null;
	}
	
	public static Character getId(OrderType type) {
		return type==null ? null : type.id;
	}
}
