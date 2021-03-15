package org.ivcode.stonks.repository.ally.model.fxml;

public enum SideOfOrder {
	/** Buy */
	BUY('1'),
	/** Sell */
	SELL('2'),
	/** Buy minus */
	BUY_MINUS('3'),
	/** Sell plus */
	SELL_PLUS('4'),
	/** Sell short */
	SELL_SHORT('5'),
	/** Sell short exempt */
	SELL_SHORT_EXEMPT('6'),
	/** Undisclosed (valid for IOI and List Order messages only) */
	UNDISCLOSED('7'),
	/**
	 * Cross (orders where counterparty is an exchange, valid for all messages
	 * except IOIs)
	 */
	CROSS('8'),
	/** Cross short */
	CROSS_SHORT('9'),
	/** Cross short exempt */
	CROSS_SHORT_EXEMPT('A'),
	/** "As Defined" (for use with multileg instruments) */
	AS_DEFINED('B'),
	/** "Opposite" (for use with multileg instruments) */
	OPPOSITE('C'),
	/** Subscribe (e.g. CIV) */
	SUBSCRIBE('D'),
	/** Redeem (e.g. CIV) */
	REDEEM('E'),
	/** Lend (FINANCING - identifies direction of collateral) */
	LEND('F'),
	/** Borrow (FINANCING - identifies direction of collateral) */
	BORROW('G');

	private final char id;

	private SideOfOrder(char id) {
		this.id = id;
	}

	public char getId() {
		return id;
	}
	
	public static SideOfOrder find(Character id) {
		if(id==null) {
			return null;
		}
		
		for(SideOfOrder side : SideOfOrder.values()) {
			if(id.equals(side.id)) {
				return side;
			}
		}
		
		return null;
	}
	
	public static Character getId(SideOfOrder side) {
		return side==null ? null : side.id;
	}
}
