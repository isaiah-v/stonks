package org.ivcode.stonks.repository.ally.model.fxml;

public enum MiscFeeType {
	/** Regulatory (e.g. SEC) */
	REGULATORY(1),
	/** Tax */
	TAX(2),
	/** Local Commission */
	LOCAL_COMMISSION(3),
	/** Exchange Fees */
	EXCHANGE_FEES(4),
	/** Stamp */
	STAMP(5),
	/** Levy */
	LEVY(6),
	/** Other */
	OTHER(7),
	/** Markup */
	MARKUP(8),
	/** Consumption Tax */
	CONSUMPTION_TAX(9),
	/** Per transaction */
	PER_TRANSACTION(10),
	/** Conversion */
	CONVERSION(11),
	/** Agent */
	AGENT(12);

	private final int id;

	private MiscFeeType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static MiscFeeType find(Integer id) {
		if(id==null) {
			return null;
		}
		
		for(MiscFeeType misc : MiscFeeType.values()) {
			if(id.equals(misc.id)) {
				return misc;
			}
		}
		
		return null;
	}
	
	public static Integer getId(MiscFeeType miscFeeType) {
		return miscFeeType==null ? null : miscFeeType.id;
	}
}
